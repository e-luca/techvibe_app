package com.projects.techvibe.access

import com.projects.techvibe.repository.access.token.ConfirmationTokenEntity
import com.projects.techvibe.repository.access.token.ConfirmationTokenRepository
import org.springframework.stereotype.Service

@Service
class ConfirmationTokenService(private val repository: ConfirmationTokenRepository) {

    fun saveToken(token: ConfirmationTokenEntity) {
        repository.save(token)
    }

    fun findByToken(token: String): ConfirmationTokenEntity? {
        return repository.findByToken(token)
    }
}
