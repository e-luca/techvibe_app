package com.projects.techvibe.model.registration

import com.projects.techvibe.model.access.AccessInfo
import com.projects.techvibe.model.user.User

data class Registration(
    val user: User,
    val accessInfo: AccessInfo,
)
