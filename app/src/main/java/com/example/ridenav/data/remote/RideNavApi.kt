package com.example.ridenav.data.remote

import com.example.ridenav.data.dto.DirectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RideNavApi {

    @GET("directions/json")
    suspend fun getRoutes(
        @Query("destination") destination: String,
        @Query("origin") origin: String,
        @Query("key") key: String
    ): Response<DirectionResponse>

}