package com.example.ridenav.data.dto

data class Driver(
    val firstName: String,
    val lastName: String,
    val vehicleType: String,
    val licencePlate: String
) {
    constructor(): this("", "", "", "")
}