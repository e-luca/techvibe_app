package com.projects.techvibe.repository.cart

import com.projects.techvibe.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : BaseRepository<CartEntity, Long> {
    fun findByUserIdAndCheckedOut(userId: Long, checkedOut: Boolean): CartEntity?
}
