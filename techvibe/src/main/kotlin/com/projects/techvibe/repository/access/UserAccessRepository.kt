package com.projects.techvibe.repository.access

import com.projects.techvibe.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccessRepository : BaseRepository<UserAccessEntity, Long> {
    fun findByEmail(email: String): UserAccessEntity?
    fun findByUsername(username: String): UserAccessEntity?

    fun findByUserId(userId: Long): UserAccessEntity?
}
