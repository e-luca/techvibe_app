package com.projects.techvibe.client.device.review

import com.projects.techvibe.model.device.review.ReviewModification
import com.projects.techvibe.repository.review.ReviewEntity
import com.projects.techvibe.repository.review.ReviewRepository
import com.projects.techvibe.repository.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val repository: ReviewRepository,
    private val userRepository: UserRepository,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(ReviewService::class.java)
    }

    fun create(deviceId: Long, userId: Long, request: ReviewModification) {
        validate(request, deviceId, userId)
        val data = ReviewEntity(request, deviceId, userId)

        try {
            repository.save(data)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while creating device review")
        }
    }

    private fun validate(request: ReviewModification, deviceId: Long, userId: Long) {
        require(request.rating in 1..5) { "Create error: Rating is not valid!" }
        require(userId > 0 && deviceId > 0) { "Create error: User ID or Device ID are not valid!" }
    }
}
