package com.projects.techvibe.admin.device_management

import com.projects.techvibe.model.device.DeviceModification
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/device-management")
class DeviceManagementController(private val service: DeviceManagementService) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = service.getDevice(id)

    @PostMapping
    fun create(@RequestBody request: DeviceModification) = service.createDevice(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: DeviceModification) = service.updateDevice(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.deleteDevice(id)
}