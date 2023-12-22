package com.projects.techvibe.client.cart

import com.projects.techvibe.model.access.UserAccess
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(private val service: CartService) {

    @GetMapping
    fun getCart(@AuthenticationPrincipal user: UserAccess) = service.findCartForUser(user.userId)

    @PostMapping("/{id}/add")
    fun addItem(@AuthenticationPrincipal user: UserAccess, @PathVariable id: Long, @RequestParam itemId: Long) = service.processItem(id, itemId, user.userId, service.ACTION_ADD)

    @PostMapping("/{id}/remove")
    fun removeItem(@AuthenticationPrincipal user: UserAccess, @PathVariable id: Long, @RequestParam itemId: Long) = service.processItem(id, itemId, user.userId, service.ACTION_DELETE)

    @PutMapping("/{id}/checkout")
    fun checkout(@PathVariable id: Long) = service.checkout(id)

    @DeleteMapping("/{id}")
    fun deleteCart(@PathVariable id: Long) = service.deleteCart(id)
}
