package com.projects.techvibe.repository.access.token

import com.projects.techvibe.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfirmationTokenRepository : BaseRepository<ConfirmationTokenEntity, Long> {

    fun findByToken(token: String): ConfirmationTokenEntity?
}
