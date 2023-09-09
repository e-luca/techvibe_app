package com.projects.techvibe.client.profile

import com.projects.techvibe.access.AccessService
import com.projects.techvibe.model.user.User
import com.projects.techvibe.model.user.UserModification
import com.projects.techvibe.repository.access.UserAccessRepository
import com.projects.techvibe.repository.user.UserEntity
import com.projects.techvibe.repository.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val userRepository: UserRepository,
    private val userAccessRepository: UserAccessRepository,
    private val accessService: AccessService,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(ProfileService::class.java)
    }

    fun getProfile(id: Long): User {
        val user = findUser(id)
        return user.convert()
    }

    fun updateProfile(id: Long, request: UserModification): User {
        val user = findUser(id)

        accessService.validateRequest(request)

        val userAccessInfo = userAccessRepository.findByUserId(id) ?: throw IllegalStateException("User access info for $id does not exist!")

        try {
            user.update(request)
            userAccessInfo.update(request)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while updating user profile")
        }

        return user.convert()
    }

    fun deleteProfile(id: Long): User {
        val user = findUser(id)

        try {
            userRepository.delete(user)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while deleting user profile")
        }

        return user.convert()
    }

    private fun findUser(id: Long): UserEntity {
        return userRepository.findByIdOrNull(id) ?: throw IllegalStateException("User not found!")
    }
}
