package com.projects.techvibe.client.device

import com.projects.techvibe.model.device.Device
import com.projects.techvibe.model.device.DeviceType
import com.projects.techvibe.repository.device.DeviceRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class DeviceService(private val repository: DeviceRepository) {

    companion object {
        private val logger = LoggerFactory.getLogger(DeviceService::class.java)
    }

    fun getByType(type: DeviceType, pageable: Pageable): Page<Device> {
        return repository.findByType(type, pageable).map { it.convert() }
    }

    fun searchByName(query: String, pageable: Pageable): Page<Device> {
        return repository.findByNameContaining(query, pageable).map { it.convert() }
    }
}