package com.projects.techvibe.model.cart

import java.time.LocalDateTime

data class CartItem(
    val itemId: Long,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val description: String,
    val quantity: Long,
    val updated: LocalDateTime,
)
