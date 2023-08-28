package com.projects.techvibe.access

import com.projects.techvibe.model.registration.Registration
import com.projects.techvibe.repository.access.UserAccessEntity
import com.projects.techvibe.repository.access.UserAccessRepository
import com.projects.techvibe.repository.user.UserEntity
import com.projects.techvibe.repository.user.UserRepository
import com.projects.techvibe.repository.user_address.UserAddressEntity
import com.projects.techvibe.repository.user_address.UserAddressRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccessService(
    private val repository: UserAccessRepository,
    private val userRepository: UserRepository,
    private val userAddressRepository: UserAddressRepository,
    private val userPasswordEncoder: BCryptPasswordEncoder,
) : UserDetailsService {

    companion object {
        private val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
        private val PASSWORD_REGEX = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,16}\$")
        private val USERNAME_REGEX = Regex("^[a-zA-Z0-9_]+\$")
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = repository.findByEmail(email) ?: throw UsernameNotFoundException("User with email $email not found!")
        return user.convert()
    }

    fun validateRequest(request: Registration): Boolean {
        return EMAIL_REGEX.matches(request.user.email) && PASSWORD_REGEX.matches(request.accessInfo.password) && USERNAME_REGEX.matches(request.user.username)
    }

    fun registerUser(request: Registration): String {
        val userExists = repository.findByEmail(request.user.email)

        if (userExists != null) throw IllegalStateException("User already exists!")

        val encodedPassword = userPasswordEncoder.encode(request.accessInfo.password)
        val savedUser = userRepository.save(UserEntity(request.user))
        val userAccessData = UserAccessEntity(savedUser.convert(), request.accessInfo)
        val userAddressData = UserAddressEntity(savedUser.id, request.address)
        userAccessData.setPassword(encodedPassword)
        repository.save(userAccessData)
        userAddressRepository.save(userAddressData)

        return ""
    }
}
