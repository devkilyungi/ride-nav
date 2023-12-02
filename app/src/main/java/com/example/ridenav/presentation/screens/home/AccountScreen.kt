package com.example.ridenav.presentation.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AccountScreen(
    onSettingsClick: () -> Unit,
    onSignOut: () -> Unit
) {
    Text(text = "Profile Screen")
}