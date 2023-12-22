package com.projects.techvibe.model.cart

import java.time.LocalDateTime

data class Cart(
    val id: Long,
    val userId: Long,
    val totalPrice: Double,
    val quantity: Int,
    val checkedOut: Boolean,
    val created: LocalDateTime,
)
