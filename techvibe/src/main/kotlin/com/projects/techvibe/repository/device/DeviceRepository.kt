package com.projects.techvibe.repository.device

import com.projects.techvibe.model.device.DeviceType
import com.projects.techvibe.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : BaseRepository<DeviceEntity, Long> {

    fun findByName(name: String): DeviceEntity?
    fun findByType(type: DeviceType, pageable: Pageable): Page<DeviceEntity>
}
