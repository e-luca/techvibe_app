package com.projects.techvibe.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository

@NoRepositoryBean
interface BaseRepository<T, ID> : CrudRepository<T, ID>, PagingAndSortingRepository<T, ID>
