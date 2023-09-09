package com.projects.techvibe.repository.user

import com.projects.techvibe.model.user.User
import com.projects.techvibe.model.user.UserModification
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "customer")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "customer_seq", allocationSize = 1)
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
    var lastLogin: LocalDateTime,
) {

    constructor(user: User) : this (
        0,
        user.firstName,
        user.lastName,
        user.username,
        user.email,
        user.dateOfBirth,
        user.imageUrl,
        LocalDate.now(),
        LocalDateTime.now(),
    )

    fun convert(): User {
        return User(id, firstName, lastName, username, email, dateOfBirth, imageUrl, registrationDate, lastLogin)
    }

    fun update(request: UserModification): UserEntity {
        firstName = request.firstName
        lastName = request.lastName
        email = request.email
        username = request.username
        imageUrl = request.imageUrl

        return this
    }
}
