package com.projects.techvibe.repository.cartitem

import com.projects.techvibe.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : BaseRepository<CartItemEntity, Long> {
    fun findByCartIdAndItemId(cartId: Long, itemId: Long): CartItemEntity?
}
