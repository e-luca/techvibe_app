package com.projects.techvibe.access

import com.projects.techvibe.model.access.AccessInfo
import com.projects.techvibe.model.user.User
import com.projects.techvibe.repository.access.UserAccessEntity
import com.projects.techvibe.repository.access.UserAccessRepository
import com.projects.techvibe.repository.user.UserEntity
import com.projects.techvibe.repository.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccessService(
    private val repository: UserAccessRepository,
    private val userRepository: UserRepository,
    private val userPasswordEncoder: BCryptPasswordEncoder,
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = repository.findByEmail(email) ?: throw UsernameNotFoundException("User with email $email not found!")
        return user.convert()
    }

    fun validateEmail(email: String): Boolean {
        return true
    }

    fun registerUser(user: User, accessInfo: AccessInfo): String {
        val userExists = repository.findByEmail(user.email)

        if (userExists != null) throw IllegalStateException("User already exists!")

        val encodedPassword = userPasswordEncoder.encode(accessInfo.password)
        val savedUser = userRepository.save(UserEntity(user))
        val userAccessData = UserAccessEntity(savedUser.convert(), accessInfo)
        userAccessData.setPassword(encodedPassword)
        repository.save(userAccessData)

        return ""
    }
}
