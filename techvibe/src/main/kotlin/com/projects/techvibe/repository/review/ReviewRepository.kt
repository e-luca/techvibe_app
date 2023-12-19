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

    @Query(
        "SELECT dr.id, dr.device_id, c.username, c.image_url, dr.rating, dr.favorite, dr.comment, dr.updated" +
            "FROM device_review dr INNER JOIN customer c ON dr.user_id = c.id WHERE dr.device_id = :deviceId ORDER BY dr.updated",
        nativeQuery = true,
    )
    fun findAllReviewsForDevice(deviceId: Long, pageable: Pageable): Page<Review>
}
