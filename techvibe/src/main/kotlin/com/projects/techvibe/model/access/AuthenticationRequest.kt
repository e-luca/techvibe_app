package com.projects.techvibe.model.access

data class AuthenticationRequest(
    val email: String,
    val password: String,
)
