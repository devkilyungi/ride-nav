package com.example.ridenav.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ridenav.presentation.screens.login.LoginScreen
import com.example.ridenav.presentation.screens.sign_up.SignUpScreen
import com.example.ridenav.presentation.screens.sign_up.UserDetailsScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.UserDetails.route)
                }
            )
        }
        composable(route = AuthScreen.UserDetails.route) {
            UserDetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onFinishClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.AUTHENTICATION)
                }
            )
        }
    }
}


sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object UserDetails : AuthScreen(route = "USER_DETAILS")
}