package com.example.ridenav

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ridenav.presentation.navigation.RootNavGraph
import com.example.ridenav.presentation.theme.RideNavTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    private lateinit var applicationViewModel: ApplicationViewModel

//    private val applicationViewModel: ApplicationViewModel = ApplicationViewModel(RideNavApp())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationViewModel = ViewModelProvider(this)[ApplicationViewModel::class.java]

        setContent {

            val location by applicationViewModel.getLocationLiveData().observeAsState()

            var locationPermissionsGranted by remember {
                mutableStateOf(
                    areLocationPermissionsAlreadyGranted()
                )
            }
            var shouldShowPermissionRationale by remember {
                mutableStateOf(
                    shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }
            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }
            var currentPermissionsStatus by remember {
                mutableStateOf(
                    decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
                )
            }
            val locationPermissions = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )

            val locationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    locationPermissionsGranted =
                        permissions.values.reduce { acc, isPermissionGranted ->
                            acc && isPermissionGranted
                        }

                    if (locationPermissionsGranted) {
                        applicationViewModel.startLocationUpdates()
                    }

                    if (!locationPermissionsGranted) {
                        shouldShowPermissionRationale =
                            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                    shouldDirectUserToApplicationSettings =
                        !shouldShowPermissionRationale && !locationPermissionsGranted
                    currentPermissionsStatus = decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
                })

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START &&
                        !locationPermissionsGranted &&
                        !shouldShowPermissionRationale
                    ) {
                        locationPermissionLauncher.launch(locationPermissions)
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            })

            val scope = rememberCoroutineScope()

            RideNavTheme {
                location?.let {
                    Log.d("Location", "Longitude: ${it.longitude}, Latitude: ${it.latitude}")
                }

                navController = rememberNavController()
                RootNavGraph(navController = rememberNavController(), location)

                if (shouldShowPermissionRationale) {
                    LaunchedEffect(Unit) {
                        scope.launch {
                            Toast.makeText(
                                this@MainActivity,
                                "Please authorize location permissions",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                if (shouldDirectUserToApplicationSettings) {
                    openApplicationSettings()
                }
            }
        }
    }

    private fun areLocationPermissionsAlreadyGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openApplicationSettings() {
        Intent(
            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also {
            startActivity(it)
        }
    }

    private fun decideCurrentPermissionStatus(
        locationPermissionsGranted: Boolean,
        shouldShowPermissionRationale: Boolean
    ): String {
        return if (locationPermissionsGranted) "Granted"
        else if (shouldShowPermissionRationale) "Rejected"
        else "Denied"
    }
}
