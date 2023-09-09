package com.projects.techvibe.admin.device_management

import com.projects.techvibe.admin.file_extractor.FileExtractorService
import com.projects.techvibe.model.device.Device
import com.projects.techvibe.model.device.DeviceModification
import com.projects.techvibe.model.device.DeviceType
import com.projects.techvibe.repository.device.DeviceEntity
import com.projects.techvibe.repository.device.DeviceRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class DeviceManagementService(
    private val repository: DeviceRepository,
    private val fileExtractor: FileExtractorService,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(DeviceManagementService::class.java)
    }

    fun getAll(pageable: Pageable): Page<Device> {
        return repository.findAll(pageable).map { it.convert() }
    }

    fun getDevice(id: Long): Device {
        val device = repository.findByIdOrNull(id) ?: throw IllegalArgumentException("Device with id $id doesn't exist!")
        return device.convert()
    }

    fun createDevice(createRequest: DeviceModification): Device {
        validateRequest(createRequest)
        val data = DeviceEntity(createRequest)
        val result: DeviceEntity
        try {
            result = repository.save(data)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while creating device")
        }

        return result.convert()
    }

    fun updateDevice(id: Long, updateRequest: DeviceModification): Device {
        val device = repository.findByIdOrNull(id) ?: return createDevice(updateRequest)
        validateRequest(updateRequest)
        device.update(updateRequest)
        try {
            repository.save(device)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while updating device")
        }

        return device.convert()
    }

    fun deleteDevice(id: Long): Device {
        val device = repository.findByIdOrNull(id) ?: throw IllegalArgumentException("Device with id $id doesn't exist!")
        try {
            repository.delete(device)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while deleting device")
        }

        return device.convert()
    }

    fun uploadDevices(fileStream: InputStream) {
        val fileLines = fileExtractor.extractCSVFile(fileStream)
        val deviceRequests = parseLines(fileLines)
        val validRequests = mutableListOf<DeviceEntity>()

        deviceRequests.forEach { request ->
            try {
                validateRequest(request)
                validRequests.add(DeviceEntity(request))
            } catch (ex: Exception) {
                logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
                throw RuntimeException("An error occurred while creating device ${request.name}. Check data in your file!")
            }
        }

        repository.saveAll(validRequests)
    }

    private fun validateRequest(request: DeviceModification) {
        require(request.name.isNotEmpty()) { "Create error: Device name must be provided!" }
        require(request.toString().isNotEmpty()) { "Create error: Device type must be provided!" }
        require(request.shortDescription.isNotEmpty()) { "Create error: Device short description must be provided!" }
        require(request.longDescription.isNotEmpty()) { "Create error: Device long description must be provided!" }
        require(request.imageUrl.isNotEmpty()) { "Create error: Device image must be provided!" }
        require(request.price >= 0) { "Create error: Device price must have positive value!" }
        require(repository.findByName(request.name) == null) { "Device with ${request.name} already exists!" }
    }

    private fun parseLines(fileLines: List<String>): List<DeviceModification> {
        val requests = fileLines.map { line ->
            val fields = line.split(",")
            try {
                val type = DeviceType.valueOf(fields[1])
                val price = fields[4].toDouble()
                val available = fields[5].toBoolean()

                DeviceModification(fields[0], type, fields[2], fields[3], price, available, fields[6])
            } catch (ex: Exception) {
                logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
                throw RuntimeException("An error occurred while parsing csv data. Check data in your file!")
            }
        }

        return requests
    }
}
