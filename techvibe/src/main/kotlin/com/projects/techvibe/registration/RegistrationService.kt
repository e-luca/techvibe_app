package com.projects.techvibe.registration

import com.projects.techvibe.access.AccessService
import com.projects.techvibe.model.registration.Registration
import org.springframework.stereotype.Service

@Service
class RegistrationService(private val accessService: AccessService) {

    fun register(request: Registration): String {
        val isValidEmail = accessService.validateEmail(request.user.email)
        if (!isValidEmail) throw IllegalArgumentException("Email is not valid!")
        return accessService.registerUser(request.user, request.accessInfo)
    }
}
