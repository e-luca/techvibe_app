package com.projects.techvibe.client.cart

import com.projects.techvibe.model.access.UserAccess
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(private val service: CartService) {

    @GetMapping
    fun getCart(@AuthenticationPrincipal user: UserAccess) = service.findCartForUser(user.userId)

    @GetMapping("/{id}/items")
    fun getItems(@PathVariable id: Long) = service.findCartItems(id)

    @PostMapping("/{itemId}/add")
    fun addItem(@AuthenticationPrincipal user: UserAccess, @PathVariable itemId: Long) = service.processItem(itemId, user.userId, service.actionAdd)

    @PostMapping("/{itemId}/remove")
    fun removeItem(@AuthenticationPrincipal user: UserAccess, @PathVariable itemId: Long) = service.processItem(itemId, user.userId, service.actionRemove)

    @PutMapping("/{id}/checkout")
    fun checkout(@PathVariable id: Long) = service.checkout(id)

    @DeleteMapping("/{id}")
    fun deleteCart(@PathVariable id: Long) = service.deleteCart(id)
}
