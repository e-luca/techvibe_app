package com.projects.techvibe.access

import com.projects.techvibe.email.EmailSender
import com.projects.techvibe.model.access.AuthenticationRequest
import com.projects.techvibe.model.registration.Registration
import com.projects.techvibe.model.user.UserModification
import com.projects.techvibe.repository.access.UserAccessEntity
import com.projects.techvibe.repository.access.UserAccessRepository
import com.projects.techvibe.repository.access.token.ConfirmationTokenEntity
import com.projects.techvibe.repository.user.UserEntity
import com.projects.techvibe.repository.user.UserRepository
import com.projects.techvibe.repository.user_address.UserAddressEntity
import com.projects.techvibe.repository.user_address.UserAddressRepository
import com.projects.techvibe.security.JwtService
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class AccessService(
    private val repository: UserAccessRepository,
    private val userRepository: UserRepository,
    private val userAddressRepository: UserAddressRepository,
    private val confirmationTokenService: ConfirmationTokenService,
    private val jwtService: JwtService,
    private val emailSender: EmailSender,
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

    fun validateRequest(request: Registration) {
        require(EMAIL_REGEX.matches(request.user.email)) { "Invalid email format!" }
        require(PASSWORD_REGEX.matches(request.accessInfo.password)) { "Invalid password format!" }
        require(USERNAME_REGEX.matches(request.user.username)) { "Invalid username format!" }
        require(repository.findByEmail(request.user.email) == null) { "Email already exists!" }
        require(repository.findByUsername(request.user.username) == null) { "Username already taken!" }
        require(request.user.firstName.isNotBlank()) { "First name should be provided!" }
        require(request.user.lastName.isNotBlank()) { "Last name should be provided!" }
    }

    fun validateRequest(request: UserModification) {
        require(EMAIL_REGEX.matches(request.email)) { "Invalid email format!" }
        require(USERNAME_REGEX.matches(request.username)) { "Invalid username format!" }
        require(request.firstName.isNotBlank()) { "First name should be provided!" }
        require(request.lastName.isNotBlank()) { "Last name should be provided!" }
    }

    fun registerUser(request: Registration, encodedPassword: String): String {
        val userExists = repository.findByEmail(request.user.email)

        if (userExists != null) throw IllegalStateException("User already exists!")

        val savedUser = userRepository.save(UserEntity(request.user))
        val userAccessData = UserAccessEntity(savedUser.convert(), request.accessInfo)
        val userAddressData = UserAddressEntity(savedUser.id, request.address)

        userAccessData.setPassword(encodedPassword)
        repository.save(userAccessData)
        userAddressRepository.save(userAddressData)

        sendConfirmationEmail(savedUser)

        return jwtService.generateToken(userAccessData.convert())
    }

    @Transactional
    fun confirmToken(token: String): String {
        val confirmationToken = confirmationTokenService.findByToken(token) ?: throw IllegalStateException("Token not found!")
        val now = LocalDateTime.now()

        if (confirmationToken.confirmedAt != null) throw IllegalStateException("Email already confirmed!")
        if (confirmationToken.expiredAt.isBefore(now)) throw IllegalStateException("Token has expired!")

        confirmationToken.confirmedAt = now

        confirmationTokenService.saveToken(confirmationToken)

        return "Email confirmed!"
    }

    fun authenticateUser(request: AuthenticationRequest): String {
        val user = repository.findByEmail(request.email) ?: throw IllegalStateException("User access info not found!")

        return jwtService.generateToken(user.convert())
    }

    private fun generateToken(userId: Long): ConfirmationTokenEntity {
        val generatedToken = UUID.randomUUID().toString()
        val now = LocalDateTime.now()
        val expiresAt = now.plusMinutes(15)
        val token = ConfirmationTokenEntity(0, userId, generatedToken, now, expiresAt, null)

        return confirmationTokenService.saveToken(token)
    }

    private fun sendConfirmationEmail(user: UserEntity) {
        val token = generateToken(user.id)
        val link = "http://localhost:8080/api/v1/auth/confirm?token=$token"
        val emailContent = emailSender.buildUserConfirmationEmail(user.firstName, link)
        emailSender.send(user.email, emailContent)
    }
}
