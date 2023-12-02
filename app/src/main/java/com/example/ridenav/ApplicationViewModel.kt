package com.example.ridenav

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ridenav.domain.LocationLiveData

class ApplicationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData = LocationLiveData(application.applicationContext)
    fun getLocationLiveData() = locationLiveData
    fun startLocationUpdates() {
        locationLiveData.startLocationUpdates()
    }
}