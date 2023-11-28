package com.example.ridenav.data.remote

import retrofit2.http.GET

interface RideNavApi {

    @GET("routes")
    suspend fun doNetworkCall()

}