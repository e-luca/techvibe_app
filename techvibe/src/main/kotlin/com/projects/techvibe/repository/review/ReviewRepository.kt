package com.projects.techvibe.repository.review

import com.projects.techvibe.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : BaseRepository<ReviewEntity, Long>
