package com.projects.techvibe.repository.cartitem

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "cart_item")
data class CartItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_generator")
    @SequenceGenerator(name = "cart_item_generator", sequenceName = "cart_item_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var cartId: Long,

    @Column(nullable = false)
    var itemId: Long,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    var updated: LocalDateTime,
)
