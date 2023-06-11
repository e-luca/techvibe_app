package com.projects.techvibe.model.device

data class Device(
    val id: Long,
    val name: String,
    val type: DeviceType,
    val shortDescription: String,
    val longDescription: String,
    val price: Double,
    val available: Boolean,
    val imageUrl: String
)
