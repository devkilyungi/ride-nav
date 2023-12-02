package com.example.ridenav.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ridenav.presentation.screens.home.AccountManagementScreen
import com.example.ridenav.presentation.screens.home.AccountScreen
import com.example.ridenav.presentation.screens.home.HistoryScreen
import com.example.ridenav.presentation.screens.home.MapScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            MapScreen()
        }
        composable(route = BottomBarScreen.History.route) {
            HistoryScreen()
        }
        composable(route = BottomBarScreen.Account.route) {
            AccountScreen(
                onSettingsClick = {
                    navController.navigate(Graph.SETTINGS)
                },
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Graph.AUTHENTICATION)
                }
            )
        }
        settingsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.settingsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.SETTINGS,
        startDestination = SettingsScreen.ManageAccount.route
    ) {
        composable(route = SettingsScreen.ManageAccount.route) {
            AccountManagementScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class SettingsScreen(val route: String) {
    object ManageAccount : SettingsScreen(route = "MANAGE_ACCOUNT")
}