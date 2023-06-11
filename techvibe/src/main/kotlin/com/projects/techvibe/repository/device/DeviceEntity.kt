package com.projects.techvibe.repository.device

import com.projects.techvibe.model.device.Device
import com.projects.techvibe.model.device.DeviceModification
import com.projects.techvibe.model.device.DeviceType
import jakarta.persistence.*

@Entity
@Table(name="device")
data class DeviceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_generator")
    @SequenceGenerator(name = "device_generator", sequenceName = "device_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: DeviceType,

    @Column(nullable = false)
    var shortDescription: String,

    @Column(nullable = false)
    var longDescription: String,

    @Column(nullable = false)
    var price: Double,

    @Column(nullable = false)
    var available: Boolean,

    @Column(nullable = false)
    var imageUrl: String
) {
    companion object {
        fun create(request: DeviceModification): DeviceEntity {
            return DeviceEntity(
                0,
                request.name,
                request.type,
                request.shortDescription,
                request.longDescription,
                request.price,
                request.available,
                request.imageUrl
            )
        }
    }

    fun convert(): Device {
        return Device(id, name, type, shortDescription, longDescription, price, available, imageUrl)
    }

    fun update(request: DeviceModification): DeviceEntity {
        name = request.name
        type = request.type
        shortDescription = request.shortDescription
        longDescription = request.longDescription
        price = request.price
        available = request.available
        imageUrl = request.imageUrl
        return this
    }
}