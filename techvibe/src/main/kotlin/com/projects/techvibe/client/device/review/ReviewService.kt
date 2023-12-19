package com.projects.techvibe.client.device.review

import com.projects.techvibe.model.device.review.Review
import com.projects.techvibe.model.device.review.ReviewModification
import com.projects.techvibe.repository.review.ReviewEntity
import com.projects.techvibe.repository.review.ReviewRepository
import com.projects.techvibe.repository.user.UserEntity
import com.projects.techvibe.repository.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ReviewService(
    private val repository: ReviewRepository,
    private val userRepository: UserRepository,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(ReviewService::class.java)
    }

    fun getForUser(deviceId: Long, userId: Long): Review? {
        val result: ReviewEntity
        val user: UserEntity

        try {
            user = userRepository.findById(userId).getOrNull() ?: throw IllegalArgumentException("User $userId cannot be found")
            result = repository.findByDeviceIdAndUserId(deviceId, userId) ?: return null
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while fetching device review!")
        }

        return result.convert(user.username, user.imageUrl)
    }

    fun getAllReviews(deviceId: Long, pageable: Pageable): Page<Review> {
        return repository.findAllReviewsForDevice(deviceId, pageable)
    }

    fun review(deviceId: Long, userId: Long, request: ReviewModification) {
        validate(request, deviceId, userId)
        val data = repository.findByDeviceIdAndUserId(deviceId, userId)

        if (data != null) {
            update(data, request)
        } else {
            create(deviceId, userId, request)
        }
    }

    fun deleteReview(deviceId: Long, userId: Long) {
        val data = repository.findByDeviceIdAndUserId(deviceId, userId) ?: throw IllegalArgumentException("Cannot find review for device $deviceId and user $userId")

        try {
            repository.delete(data)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while deleting device review!")
        }
    }

    private fun create(deviceId: Long, userId: Long, request: ReviewModification) {
        val data = ReviewEntity(request, deviceId, userId)

        try {
            repository.save(data)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while creating device review!")
        }
    }

    private fun update(data: ReviewEntity, request: ReviewModification) {
        data.update(request)

        try {
            repository.save(data)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while updating device review!")
        }
    }

    private fun validate(request: ReviewModification, deviceId: Long, userId: Long) {
        require(request.rating in 1..5) { "Create error: Rating is not valid!" }
        require(userId > 0 && deviceId > 0) { "Create error: User ID or Device ID are not valid!" }
    }
}
