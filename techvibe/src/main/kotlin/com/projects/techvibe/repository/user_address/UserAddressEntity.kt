package com.projects.techvibe.repository.user_address

import com.projects.techvibe.model.user_address.UserAddress
import jakarta.persistence.*

@Entity
@Table(name = "user_address")
data class UserAddressEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_address_generator")
    @SequenceGenerator(name = "user_address_generator", sequenceName = "user_address_seq", allocationSize = 1)
    var id: Long,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var streetName: String,

    @Column(nullable = false)
    var streetNumber: String,

    @Column(nullable = false)
    var city: String,

    @Column(nullable = false)
    var state: String,

    @Column(nullable = false)
    var zip: String,

    @Column(nullable = false)
    var country: String,

    @Column(nullable = false)
    var latitude: String,

    @Column(nullable = false)
    var longitude: String,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var notes: String,
) {
    constructor(userId: Long, address: UserAddress) : this (
        0,
        userId,
        address.streetName,
        address.streetNumber,
        address.city,
        address.state,
        address.zip,
        address.country,
        address.latitude,
        address.longitude,
        address.type,
        address.notes,
    )
}
