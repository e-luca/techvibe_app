package com.projects.techvibe.repository.cartitem

import com.projects.techvibe.model.cart.CartItem
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@NamedNativeQuery(
    name = "find_cart_items",
    query = "SELECT ci.item_id, d.name, d.image_url, d.price, d.short_description, ci.quantity, ci.updated " +
        "FROM cart_item ci INNER JOIN device d ON ci.item_id = d.id WHERE ci.cart_id = :cartId ORDER BY ci.updated",
    resultSetMapping = "CartItemEntityMapping",
)
@SqlResultSetMapping(
    name = "CartItemEntityMapping",
    classes = [
        ConstructorResult(
            targetClass = CartItem::class,
            columns = [
                ColumnResult(name = "item_id", type = Long::class),
                ColumnResult(name = "name", type = String::class),
                ColumnResult(name = "image_url", type = String::class),
                ColumnResult(name = "price", type = Double::class),
                ColumnResult(name = "short_description", type = String::class),
                ColumnResult(name = "quantity", type = Int::class),
                ColumnResult(name = "updated", type = LocalDateTime::class),
            ],
        ),
    ],
)
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
