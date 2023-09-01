package com.projects.techvibe.auth

import com.projects.techvibe.access.AccessService
import com.projects.techvibe.model.registration.Registration
import org.springframework.stereotype.Service

@Service
class AuthService(private val accessService: AccessService) {

    fun register(request: Registration) {
        val isValidRequest = accessService.validateRequest(request)
        if (!isValidRequest) throw IllegalArgumentException("Request is not valid!")
        return accessService.registerUser(request)
    }

    fun confirmUser(token: String): String {
        return accessService.confirmToken(token)
    }
}
