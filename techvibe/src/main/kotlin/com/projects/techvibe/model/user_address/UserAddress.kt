package com.projects.techvibe.model.user_address

data class UserAddress(
    var streetName: String,
    var streetNumber: String,
    var city: String,
    var state: String,
    var zip: String,
    var country: String,
    var latitude: String,
    var longitude: String,
    var type: String,
    var notes: String,
)
