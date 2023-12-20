package com.projects.techvibe.repository.review

import com.projects.techvibe.model.device.review.Review
import com.projects.techvibe.model.device.review.ReviewModification
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@NamedNativeQuery(
    name = "find_all_reviews",
    query = "SELECT dr.id, dr.device_id, c.username, c.image_url, dr.rating, dr.favorite, dr.comment, dr.updated " +
        "FROM device_review dr INNER JOIN customer c ON dr.user_id = c.id WHERE dr.device_id = :deviceId ORDER BY dr.updated",
    resultSetMapping = "ReviewEntityMapping",
)
@SqlResultSetMapping(
    name = "ReviewEntityMapping",
    classes = [
        ConstructorResult(
            targetClass = Review::class,
            columns = [
                ColumnResult(name = "id", type = Long::class),
                ColumnResult(name = "device_id", type = Long::class),
                ColumnResult(name = "username", type = String::class),
                ColumnResult(name = "image_url", type = String::class),
                ColumnResult(name = "rating", type = Int::class),
                ColumnResult(name = "favorite", type = Boolean::class),
                ColumnResult(name = "comment", type = String::class),
                ColumnResult(name = "updated", type = LocalDateTime::class),
            ],
        ),
    ],
)
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

    fun convert(username: String, imageUrl: String) = Review(id, deviceId, username, imageUrl, rating, favorite, comment, updated)

    fun update(request: ReviewModification): ReviewEntity {
        rating = request.rating
        favorite = request.favorite
        comment = request.comment

        return this
    }
}
