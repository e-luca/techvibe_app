package com.projects.techvibe.repository.cart

import com.projects.techvibe.model.cart.Cart
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "cart")
data class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_generator")
    @SequenceGenerator(name = "cart_generator", sequenceName = "cart_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var totalPrice: Double,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    var checkedOut: Boolean,

    @Column(nullable = false)
    var created: LocalDateTime,
) {
    constructor(userId: Long) : this(
        0,
        userId,
        0.0,
        0,
        false,
        LocalDateTime.now(),
    )

    fun convert() = Cart(id, userId, totalPrice, quantity, checkedOut, created)

    fun updateCheckout() {
        checkedOut = !checkedOut
    }
}
