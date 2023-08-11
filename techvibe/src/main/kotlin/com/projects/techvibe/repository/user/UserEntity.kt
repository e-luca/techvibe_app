package com.projects.techvibe.repository.user

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime


@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var dateOfBirth: LocalDate,

    @Column(nullable = false)
    var imageUrl: String,

    @Column(nullable = false)
    var registrationDate: LocalDate,

    @Column(nullable = false)
    var lastLogin: LocalDateTime
)