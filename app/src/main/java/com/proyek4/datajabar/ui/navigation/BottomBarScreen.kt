package com.proyek4.datajabar.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen("home", "Home", Icons.Filled.Home)
    object Profile : BottomBarScreen("profile", "Profile", Icons.Filled.Person)
    object Settings : BottomBarScreen("settings", "Settings", Icons.Filled.Settings)
}