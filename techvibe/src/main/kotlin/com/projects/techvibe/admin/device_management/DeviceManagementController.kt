package com.projects.techvibe.admin.device_management

import com.projects.techvibe.model.device.DeviceModification
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/admin/device-management")
class DeviceManagementController(private val service: DeviceManagementService) {

    @GetMapping
    fun getAll(pageable: Pageable) = service.getAll(pageable)

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = service.getDevice(id)

    @PostMapping
    fun create(@RequestBody request: DeviceModification) = service.createDevice(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: DeviceModification) = service.updateDevice(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.deleteDevice(id)

    @PostMapping("/csv")
    fun uploadCSVFile(@RequestParam file: MultipartFile) = service.uploadDevices(file.inputStream)
}