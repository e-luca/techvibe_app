package com.projects.techvibe.repository.device

import com.projects.techvibe.model.device.DeviceType
import com.projects.techvibe.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : BaseRepository<DeviceEntity, Long> {

    fun findByName(name: String): DeviceEntity?
    fun findByType(type: DeviceType, pageable: Pageable): Page<DeviceEntity>
    fun findByNameContaining(name: String, pageable: Pageable): Page<DeviceEntity>

    @Query(
        "SELECT d.* FROM device d INNER JOIN device_review dr ON d.id = dr.device_id " +
            "WHERE dr.user_id = :userId AND dr.favorite = TRUE ORDER BY dr.created",
        nativeQuery = true,
    )
    fun findFavoritesForUser(userId: Long, pageable: Pageable): Page<DeviceEntity>
}
