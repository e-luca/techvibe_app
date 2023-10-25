package com.projects.techvibe.auth

import com.projects.techvibe.access.AccessService
import com.projects.techvibe.model.access.AuthenticationRequest
import com.projects.techvibe.model.access.SecurityQuestions
import com.projects.techvibe.model.registration.Registration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val accessService: AccessService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    fun register(request: Registration): String {
        accessService.validateRequest(request)
        val encodedPassword = passwordEncoder.encode(request.accessInfo.password)

        return accessService.registerUser(request, encodedPassword)
    }

    fun confirmUser(token: String): String {
        return accessService.confirmToken(token)
    }

    fun getSecurityQuestions(): List<String> {
        return SecurityQuestions.values().map { it.value }
    }

    fun authenticate(request: AuthenticationRequest): String {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        return accessService.authenticateUser(request)
    }
}
