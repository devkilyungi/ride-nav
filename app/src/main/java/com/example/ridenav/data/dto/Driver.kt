package com.example.ridenav.data.dto

data class Driver(
    val firstName: String,
    val lastName: String,
    val licencePlate: String,
    val email: String,
    val vehicleType: String
) {
    constructor(): this( "", "", "", "", "")
}