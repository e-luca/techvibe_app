package com.projects.techvibe.client.device

import com.projects.techvibe.client.device.review.ReviewService
import com.projects.techvibe.model.access.UserAccess
import com.projects.techvibe.model.device.DeviceType
import com.projects.techvibe.model.device.review.ReviewModification
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/device")
class DeviceController(
    private val service: DeviceService,
    private val reviewService: ReviewService,
) {
    @GetMapping
    fun getByType(@RequestParam type: DeviceType, pageable: Pageable) = service.getByType(type, pageable)

    @GetMapping("/search")
    fun searchByName(@RequestParam query: String, pageable: Pageable) = service.searchByName(query, pageable)

    @GetMapping("/review/{id}")
    fun getReview(@AuthenticationPrincipal user: UserAccess, @PathVariable id: Long) = reviewService.getForUser(id, user.userId)

    @GetMapping("/review/{id}/all")
    fun getReviews(@PathVariable id: Long, pageable: Pageable) = reviewService.getAllReviews(id, pageable)

    @PostMapping("/review/{id}")
    fun review(@AuthenticationPrincipal user: UserAccess, @PathVariable id: Long, @RequestBody request: ReviewModification) = reviewService.review(id, user.userId, request)

    @DeleteMapping("/review/{id}")
    fun delete(@AuthenticationPrincipal user: UserAccess, @PathVariable id: Long) = reviewService.deleteReview(id, user.userId)
}
