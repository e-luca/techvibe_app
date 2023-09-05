package com.projects.techvibe.auth

import com.projects.techvibe.model.registration.Registration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val service: AuthService) {

    @PostMapping("/registration")
    fun register(@RequestBody request: Registration) = service.register(request)

    @PostMapping("/confirm")
    fun confirm(@RequestParam token: String): String = service.confirmUser(token)

    @GetMapping("/questions")
    fun getSecurityQuestions(): List<String> = service.getSecurityQuestions()
}
