package com.projects.techvibe.model.device.review

import java.time.LocalDateTime

data class Review(
    val id: Long,
    val deviceId: Long,
    val email: String,
    val imageUrl: String,
    val rating: Int,
    val favorite: Boolean,
    val comment: String,
    val updated: LocalDateTime,
)
