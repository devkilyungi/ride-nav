package com.example.ridenav.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )

    object History : BottomBarScreen(
        route = "HISTORY",
        title = "HISTORY",
        icon = Icons.Default.History
    )

    object Account : BottomBarScreen(
        route = "ACCOUNT",
        title = "ACCOUNT",
        icon = Icons.Default.Person
    )
}
