package com.example.ridenav.data.repository

import android.app.Application
import com.example.ridenav.common.Resource
import com.example.ridenav.data.dto.DirectionResponse
import com.example.ridenav.data.remote.RideNavApi
import com.example.ridenav.domain.repository.MapRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appContext: Application,
    private val api: RideNavApi
) : MapRepository {
    override suspend fun getRoutes(
        destination: String,
        origin: String,
        key: String
    ): Flow<Resource<DirectionResponse?>> {
        return flow {
            emit(value = Resource.Loading())
            val result = api.getRoutes(destination, origin, key)
            if (result.isSuccessful) emit(value = Resource.Success(data = result.body()))
            else emit(Resource.Error(result.errorBody().toString()))
        }.catch {
            emit(value = Resource.Error(it.message.toString()))
        }
    }
}