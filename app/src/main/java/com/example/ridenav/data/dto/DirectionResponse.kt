package com.example.ridenav.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DirectionResponse(
    val routes: List<Route>,
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val status: String
)

@Serializable
data class GeocodedWaypoint(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>
)

@Serializable
data class Route(
    val legs: List<Leg>,
    val bounds: Bounds,
    val copyrights: String
)

@Serializable
data class Bounds(
    val northeast: Location,
    val southwest: Location
)

@Serializable
data class Location(
    val lat: Double,
    val lng: Double
)

@Serializable
data class Leg(
    val distance: Distance,
    val duration: Duration,
    val end_address: String,
    val end_location: Location,
    val start_address: String,
    val start_location: Location,
    val steps: List<Step>
)

@Serializable
data class Distance(
    val text: String,
    val value: Int
)

@Serializable
data class Duration(
    val text: String,
    val value: Int
)

@Serializable
data class Step(
    val distance: Distance,
    val duration: Duration,
    val end_location: Location,
    val html_instructions: String,
    val polyline: Polyline,
    val start_location: Location,
    val travel_mode: String
)

@Serializable
data class Polyline(
    val points: String
)

