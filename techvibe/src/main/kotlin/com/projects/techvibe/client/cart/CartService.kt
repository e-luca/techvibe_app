package com.projects.techvibe.client.cart

import com.projects.techvibe.model.cart.Cart
import com.projects.techvibe.repository.cart.CartEntity
import com.projects.techvibe.repository.cart.CartRepository
import com.projects.techvibe.repository.cartitem.CartItemEntity
import com.projects.techvibe.repository.cartitem.CartItemRepository
import com.projects.techvibe.repository.device.DeviceEntity
import com.projects.techvibe.repository.device.DeviceRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CartService(
    private val repository: CartRepository,
    private val deviceRepository: DeviceRepository,
    private val cartItemRepository: CartItemRepository,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(CartService::class.java)
    }

    val actionAdd = "Add"
    val actionRemove = "Remove"

    fun findCartForUser(userId: Long): Cart {
        try {
            val cart = repository.findByUserIdAndCheckedOut(userId, false) ?: throw IllegalArgumentException("Cart doesn't exist for user $userId!")
            return cart.convert()
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while fetching cart for user")
        }
    }

    fun processItem(itemId: Long, userId: Long, action: String) {
        val cart = repository.findByUserIdAndCheckedOut(userId, false) ?: createCart(userId)

        try {
            val device = deviceRepository.findByIdOrNull(itemId) ?: throw IllegalArgumentException("Device with id $itemId doesn't exist!")
            val cartItem = cartItemRepository.findByCartIdAndItemId(cart.id, itemId) ?: CartItemEntity(0, cart.id, itemId, 0, LocalDateTime.now())

            if (action == actionAdd) {
                addItemToCart(cart, cartItem, device)
            } else {
                deleteItemFromCart(cart, cartItem, device)
            }
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while adding/deleting item to/from cart")
        }
    }

    fun checkout(cartId: Long) {
        try {
            val cart = repository.findByIdOrNull(cartId) ?: throw IllegalArgumentException("Cart $cartId doesn't exist")
            cart.updateCheckout()
            repository.save(cart)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while updating cart")
        }
    }

    fun deleteCart(cartId: Long) {
        try {
            repository.deleteById(cartId)
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while deleting cart")
        }
    }

    private fun addItemToCart(cart: CartEntity, cartItem: CartItemEntity, device: DeviceEntity) {
        cartItem.quantity++
        cartItem.updated = LocalDateTime.now()
        cart.quantity++
        cart.totalPrice += device.price
        repository.save(cart)
        cartItemRepository.save(cartItem)
    }

    private fun deleteItemFromCart(cart: CartEntity, cartItem: CartItemEntity, device: DeviceEntity) {
        if (cartItem.quantity > 1) {
            cartItem.quantity--
            cartItem.updated = LocalDateTime.now()
        } else {
            cartItemRepository.delete(cartItem)
        }

        cart.quantity--
        cart.totalPrice -= device.price
        repository.save(cart)
    }

    private fun createCart(userId: Long): CartEntity {
        try {
            return repository.save(CartEntity(userId))
        } catch (ex: Exception) {
            logger.debug("Message: ${ex.message}, Cause: ${ex.cause}")
            throw RuntimeException("An error occurred while creating cart")
        }
    }
}
