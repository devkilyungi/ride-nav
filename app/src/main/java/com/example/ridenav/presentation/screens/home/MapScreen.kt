package com.example.ridenav.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ridenav.data.dto.LocationDetails
import com.example.ridenav.presentation.components.Notice
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

data class LocationCoord(
    val lat: String,
    val long: String
) {
    constructor() : this("", "")
}

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(), padding: Modifier, location: LocationDetails?
) {

    val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbUsers: CollectionReference = dB.collection("driverLocation")
    val users = ArrayList<LatLng>()

    dbUsers
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                for (document in documents) {
                    val locationCoordinates = document.toObject<LocationCoord>()
                    users.add(
                        LatLng(
                            locationCoordinates.lat.toDouble(),
                            locationCoordinates.long.toDouble()
                        )
                    )
                }

                Log.d("TAG", "DocumentSnapshot data: ")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("TAG", "Error getting collection: ", exception)
        }


    val currentLocation = LatLng(
        location?.latitude!!.toDouble(),
        location?.longitude!!.toDouble()
    )

    var shouldShowRecommendations by remember {
        mutableStateOf(false)
    }

    viewModel.getRoutes(
        destination = "-1.231235,36.883648",
        origin = "${location?.latitude},${location?.longitude}",
        key = "AIzaSyAbMSoGyYT9iQUJpQy8Ij9WA1pdXimYP1k",
        shouldShow = shouldShowRecommendations
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation,
            10f
        )
    }

    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    Box(modifier = padding) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            Circle(
                center = currentLocation,
                fillColor = MaterialTheme.colors.primary
            )

            // 1
            Marker(
                state = MarkerState(
                    position = LatLng(
                        -1.230442,
                        36.880345
                    )
                ),
                title = "Rider",
                snippet = "Rider here"
            )

            Marker(
                state = MarkerState(
                    position = LatLng(
                        -1.230043,
                        36.880370
                    )
                ),
                title = "Rider",
                snippet = "Rider here"
            )

            Marker(
                state = MarkerState(
                    position = LatLng(
                        -1.230020,
                        36.879960
                    )
                ),
                title = "Rider",
                snippet = "Rider here"
            )

            Marker(
                state = MarkerState(
                    position = LatLng(
                        -1.231866,
                        36.881277
                    )
                ),
                title = "Rider",
                snippet = "Rider here"
            )

            // 0
            Marker(
                state = MarkerState(
                    position = LatLng(
                        -1.2312309,
                        36.8836701
                    )
                ),
                title = "Rider",
                snippet = "Rider here"
            )

            // driver marker
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Driver",
                snippet = "You are here",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )

            // Just once
            LaunchedEffect(Unit) {
                delay(4.seconds)
                shouldShowRecommendations = true
            }

            if (!shouldShowRecommendations) {
                Polyline(
                    points = listOf(
                        currentLocation,
                        LatLng(
                            -1.2316132,
                            36.8848671
                        ),
                        LatLng(
                            -1.2320384,
                            36.8838371
                        ),
                        LatLng(
                            -1.2320384,
                            36.8838371
                        ),
                        LatLng(
                            -1.2312309,
                            36.8836701
                        )
                    )
                )
            }

                Polyline(
                    points = listOf(
                        currentLocation,
                        LatLng(
                            -1.2316132,
                            36.8848671
                        ),
                        LatLng(
                            -1.2320384,
                            36.8838371
                        ),
                        LatLng(
                            -1.2320384,
                            36.8838371
                        ),
                        LatLng(
                            -1.2337698,
                            36.8845182
                        ),
                        LatLng(
                            -1.2337698,
                            36.8845182
                        ),
                        LatLng(
                            -1.234573,
                            36.8826901
                        ),
                        LatLng(
                            -1.234573,
                            36.8826901
                        ),
                        LatLng(
                            -1.2328997,
                            36.88202709999999
                        ),
                        LatLng(
                            -1.2328997,
                            36.88202709999999
                        ),
                        LatLng(
                            -1.2304257,
                            36.880368
                        )
                    )
                )


        }
    }

    // Information notice
    Notice(visible = viewModel.informationNotice,
        header = "Notice",
        content = viewModel.informationNoticeContent,
        primaryButtonText = "Show Recommendation",
        primaryOnClick = {
            viewModel.closeNotice()
        },
        secondaryButtonText = "Cancel",
        secondaryOnClick = {
            if (viewModel.counter < 2) {
                viewModel.counter++
            } else {
                viewModel.closeNotice()
            }
        }
    )
}