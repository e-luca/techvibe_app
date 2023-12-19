package com.projects.techvibe.repository.review

import com.projects.techvibe.model.device.review.Review
import com.projects.techvibe.model.device.review.ReviewModification
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "device_review")
data class ReviewEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_generator")
    @SequenceGenerator(name = "review_generator", sequenceName = "device_review_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var deviceId: Long,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var rating: Int,

    @Column(nullable = false)
    var favorite: Boolean,

    @Column(nullable = false)
    var comment: String,

    @Column(nullable = false)
    var created: LocalDateTime,

    @Column(nullable = false)
    var updated: LocalDateTime,
) {

    constructor(
        request: ReviewModification,
        deviceId: Long,
        userId: Long,
    ) : this (
        0,
        deviceId,
        userId,
        request.rating,
        request.favorite,
        request.comment,
        LocalDateTime.now(),
        LocalDateTime.now(),
    )

    fun convert(email: String, imageUrl: String) = Review(id, deviceId, email, imageUrl, rating, favorite, comment, updated)

    fun update(request: ReviewModification): ReviewEntity {
        rating = request.rating
        favorite = request.favorite
        comment = request.comment

        return this
    }
}
