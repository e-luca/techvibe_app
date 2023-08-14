package com.projects.techvibe.repository.access

import com.projects.techvibe.repository.BaseRepository

interface UserAccessRepository : BaseRepository<UserAccessEntity, Long> {
    fun findByEmail(email: String): UserAccessEntity?
}
