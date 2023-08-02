package com.projects.techvibe.repository.device

import com.projects.techvibe.model.device.DeviceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<DeviceEntity, Long>, PagingAndSortingRepository<DeviceEntity, Long> {

    fun findByName(name: String): DeviceEntity?
    fun findByType(type: DeviceType, pageable: Pageable): Page<DeviceEntity>
}
