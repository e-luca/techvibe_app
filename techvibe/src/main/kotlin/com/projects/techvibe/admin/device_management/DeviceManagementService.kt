package com.projects.techvibe.admin.device_management

import com.projects.techvibe.model.device.Device
import com.projects.techvibe.model.device.DeviceModification
import com.projects.techvibe.repository.device.DeviceEntity
import com.projects.techvibe.repository.device.DeviceRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DeviceManagementService(private val repository: DeviceRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(DeviceManagementService::class.java)
    }

    fun getDevice(id: Long): Device {
        val device = repository.findByIdOrNull(id) ?: throw IllegalArgumentException("Device with id $id doesn't exist!")
        return device.convert()
    }

    fun createDevice(createRequest: DeviceModification): Device {
        validateRequest(createRequest)
        val data = DeviceEntity.create(createRequest)
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

    private fun validateRequest(request: DeviceModification) {
        require(request.name.isNotEmpty()) { "Create error: Device name must be provided!" }
        require(request.toString().isNotEmpty()) { "Create error: Device type must be provided!" }
        require(request.shortDescription.isNotEmpty()) { "Create error: Device short description must be provided!" }
        require(request.longDescription.isNotEmpty()) { "Create error: Device long description must be provided!" }
        require(request.imageUrl.isNotEmpty()) { "Create error: Device image must be provided!" }
        require(request.price >= 0) { "Create error: Device price must have positive value!" }
    }
}