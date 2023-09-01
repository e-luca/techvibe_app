package com.projects.techvibe.repository.access

import com.projects.techvibe.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccessRepository : BaseRepository<UserAccessEntity, Long> {
    fun findByEmail(email: String): UserAccessEntity?
}
