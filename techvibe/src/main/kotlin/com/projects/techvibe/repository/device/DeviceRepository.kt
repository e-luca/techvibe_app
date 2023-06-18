package com.projects.techvibe.repository.device

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<DeviceEntity, Long>, PagingAndSortingRepository<DeviceEntity, Long>