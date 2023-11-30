package com.example.ridenav.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen(route = "login_screen")
    object SignUpScreen : Screen(route = "sign_in_screen")
    object UserDetailsScreen: Screen(route = "user_details_screen")
    object HomeScreen : Screen(route = "home_screen")
    object ProfileScreen : Screen(route = "profile_screen")
}