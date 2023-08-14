package com.projects.techvibe.repository.access

import com.projects.techvibe.model.access.AccessInfo
import com.projects.techvibe.model.access.RolesConverter
import com.projects.techvibe.model.access.UserAccess
import com.projects.techvibe.model.access.UserRoles
import com.projects.techvibe.model.user.User
import jakarta.persistence.*

@Entity
@Table(name = "user_access")
data class UserAccessEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_access_generator")
    @SequenceGenerator(name = "user_access_generator", sequenceName = "user_access_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Convert(converter = RolesConverter::class)
    @Column(nullable = false)
    var roles: Set<UserRoles> = setOf(),

    @Column(nullable = false)
    var locked: Boolean,

    @Column(nullable = false)
    var question: String,

    @Column(nullable = false)
    var answer: String,
) {

    constructor(user: User, accessInfo: AccessInfo) : this (
        0,
        user.id,
        user.email,
        user.username,
        accessInfo.password,
        setOf(UserRoles.USER),
        false,
        accessInfo.question,
        accessInfo.answer,
    )

    fun convert(): UserAccess {
        return UserAccess(id, userId, email, roles, locked, question, answer)
    }

    fun setPassword(newPassword: String): UserAccessEntity {
        password = newPassword
        return this
    }
}
