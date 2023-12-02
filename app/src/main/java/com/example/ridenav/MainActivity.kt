package com.example.ridenav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ridenav.presentation.navigation.RootNavGraph
import com.example.ridenav.presentation.theme.RideNavTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideNavTheme {
                navController = rememberNavController()
                RootNavGraph(navController = rememberNavController())
            }
        }
    }
}
