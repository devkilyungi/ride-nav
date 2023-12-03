package com.example.ridenav.di

import android.app.Application
import com.example.ridenav.data.remote.RideNavApi
import com.example.ridenav.data.repository.AuthRepositoryImpl
import com.example.ridenav.data.repository.MapRepositoryImpl
import com.example.ridenav.domain.repository.AuthRepository
import com.example.ridenav.domain.repository.MapRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRideNavApi(): RideNavApi {
        return Retrofit.Builder().apply {
            baseUrl("https://maps.googleapis.com/maps/api/")
            addConverterFactory(GsonConverterFactory.create())
        }
            .build()
            .create(RideNavApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        app: Application,
        api: RideNavApi
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth, app, api)

    @Provides
    @Singleton
    fun provideMapRepository(
        firebaseAuth: FirebaseAuth,
        app: Application,
        api: RideNavApi
    ): MapRepository = MapRepositoryImpl(firebaseAuth, app, api)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}