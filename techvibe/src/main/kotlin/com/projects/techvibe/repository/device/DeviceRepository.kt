package com.projects.techvibe.repository.device

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<DeviceEntity, Long>
