package com.example.ridenav.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ridenav.data.dto.LocationDetails
import com.example.ridenav.presentation.screens.home.HomeScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    location: LocationDetails?
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen(location = location)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val SETTINGS = "settings_graph"
}