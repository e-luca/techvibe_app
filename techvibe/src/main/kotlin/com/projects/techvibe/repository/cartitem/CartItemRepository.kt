package com.projects.techvibe.repository.cartitem

import com.projects.techvibe.model.cart.CartItem
import com.projects.techvibe.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : BaseRepository<CartItemEntity, Long> {
    fun findByCartIdAndItemId(cartId: Long, itemId: Long): CartItemEntity?
    @Query(name = "find_cart_items", nativeQuery = true)
    fun findItemsByCartId(cartId: Long): List<CartItem>
}
