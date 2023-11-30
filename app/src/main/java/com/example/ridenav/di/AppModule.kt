package com.example.ridenav.di

import android.app.Application
import com.example.ridenav.data.remote.RideNavApi
import com.example.ridenav.data.repository.AuthRepositoryImpl
import com.example.ridenav.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRideNavApi(): RideNavApi {
        return Retrofit.Builder()
            .baseUrl("https://")
            .build()
            .create(RideNavApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        app: Application
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth, app)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}