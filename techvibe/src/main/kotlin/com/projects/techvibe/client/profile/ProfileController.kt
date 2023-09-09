package com.projects.techvibe.client.profile

import com.projects.techvibe.model.user.UserModification
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profile")
class ProfileController(private val service: ProfileService) {

    @GetMapping("/{id}")
    fun getUserProfile(@PathVariable id: Long) = service.getProfile(id)

    @PutMapping("/{id}")
    fun updateUserProfile(@PathVariable id: Long, @RequestBody request: UserModification) = service.updateProfile(id, request)

    @DeleteMapping("/{id}")
    fun deleteUserProfile(@PathVariable id: Long) = service.deleteProfile(id)
}
