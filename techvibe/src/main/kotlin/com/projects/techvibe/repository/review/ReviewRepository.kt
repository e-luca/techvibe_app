package com.projects.techvibe.repository.review

import com.projects.techvibe.model.device.review.Review
import com.projects.techvibe.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : BaseRepository<ReviewEntity, Long> {

    fun findByDeviceIdAndUserId(deviceId: Long, userId: Long): ReviewEntity?

    @Query(name = "find_all_reviews", nativeQuery = true)
    fun findAllReviewsForDevice(deviceId: Long, pageable: Pageable): Page<Review>
}
