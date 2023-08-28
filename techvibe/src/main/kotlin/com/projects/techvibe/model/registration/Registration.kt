package com.projects.techvibe.model.registration

import com.projects.techvibe.model.access.AccessInfo
import com.projects.techvibe.model.user.User
import com.projects.techvibe.model.user_address.UserAddress

data class Registration(
    val user: User,
    val accessInfo: AccessInfo,
    val address: UserAddress,
)
