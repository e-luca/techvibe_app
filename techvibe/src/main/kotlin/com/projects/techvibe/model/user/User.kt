package com.projects.techvibe.model.user

import java.time.LocalDate
import java.time.LocalDateTime

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val dateOfBirth: LocalDate,
    val imageUrl: String,
    val registrationDate: LocalDate,
    val lastLogin: LocalDateTime,
)
