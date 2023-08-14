package com.projects.techvibe.registration

import com.projects.techvibe.model.registration.Registration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/registration")
class RegistrationController(private val service: RegistrationService) {

    @PostMapping
    fun register(@RequestBody request: Registration): String = service.register(request)
}
