package com.projects.techvibe.model.access

enum class UserRoles(val role: String) {
    ADMIN("Admin"),
    USER("User"),
    PRODUCT_MANAGER("Product Manager"),
    ORDER_MANAGER("Order Manager"),
    CUSTOMER_SUPPORT("Customer Support"),
    CONTENT_MANAGER("Content Manager"),
    ANALYTICS_MANAGER("Analytics Manager"),
}
