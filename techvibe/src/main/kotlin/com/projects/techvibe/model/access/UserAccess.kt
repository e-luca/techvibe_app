package com.projects.techvibe.model.access

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

data class UserAccess(
    val id: Long,
    val userId: Long,
    val email: String,
    val roles: Set<UserRoles>,
    val locked: Boolean,
    val question: String,
    val answer: String,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = roles.map { SimpleGrantedAuthority(it.name) }
        return Collections.unmodifiableCollection(authorities)
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return !locked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
