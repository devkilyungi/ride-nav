package com.example.ridenav.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ridenav.data.dto.LocationDetails
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(padding: Modifier, location: LocationDetails?) {

    val currentLocation = LatLng(
        location?.latitude!!.toDouble(),
        location?.longitude!!.toDouble()
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation,
            10f
        )
    }

    Box(modifier = padding) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = currentLocation),
                title = "User Location",
                snippet = "Marker in user's location"
            )
        }
    }
}