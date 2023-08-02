package com.projects.techvibe.user.device

import com.projects.techvibe.model.device.DeviceType
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/device")
class DeviceController(private val service: DeviceService) {
    @GetMapping
    fun getByType(@RequestParam type: DeviceType, pageable: Pageable) = service.getByType(type, pageable)
}