package com.projects.techvibe.repository.access

import org.springframework.data.repository.PagingAndSortingRepository

interface UserAccessRepository : PagingAndSortingRepository<UserAccessEntity, Long> {
}