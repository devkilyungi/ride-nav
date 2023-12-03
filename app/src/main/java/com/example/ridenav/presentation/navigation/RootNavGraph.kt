package com.example.ridenav.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ridenav.R
import com.example.ridenav.data.dto.LocationDetails
import com.example.ridenav.presentation.screens.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RootNavGraph(
    navController: NavHostController,
    location: LocationDetails?,
    auth: FirebaseAuth
) {

    DisposableEffect(auth) {
        // Create the AuthStateListener
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            // Handle authentication state changes
            if (auth.currentUser != null) {
                // User is signed in
                navController.navigate(Graph.HOME)
            } else {
                // User is signed out
                navController.navigate(Graph.AUTHENTICATION)
            }
        }

        // Add the auth state listener when the composable is first created
        auth.addAuthStateListener(authStateListener)

        // Remove the listener when the composable is disposed
        onDispose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        composable(route = Graph.SPLASH) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp),
                )
            }
        }
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen(location = location)
        }
    }
}

object Graph {
    const val SPLASH = "splash"
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val SETTINGS = "settings_graph"
}