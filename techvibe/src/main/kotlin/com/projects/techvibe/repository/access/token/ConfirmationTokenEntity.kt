package com.projects.techvibe.repository.access.token

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "confirmation_token")
data class ConfirmationTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
    @SequenceGenerator(name = "token_generator", sequenceName = "confirmation_token_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var token: String,

    @Column(nullable = false)
    var createdAt: LocalDateTime,

    @Column(nullable = false)
    var expiredAt: LocalDateTime,

    var confirmedAt: LocalDateTime?,
)
