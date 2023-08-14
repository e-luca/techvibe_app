package com.projects.techvibe.repository.user

import com.projects.techvibe.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : BaseRepository<UserEntity, Long>
