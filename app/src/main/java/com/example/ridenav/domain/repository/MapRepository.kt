package com.example.ridenav.domain.repository

import com.example.ridenav.common.Resource
import com.example.ridenav.data.dto.DirectionResponse
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun getRoutes(
        destination: String,
        origin: String,
        key: String
    ): Flow<Resource<DirectionResponse?>>
}