package com.proyek4.datajabar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.proyek4.datajabar.ui.screen.home.HomeScreen
import com.proyek4.datajabar.ui.screen.profile.ProfileScreen
import com.proyek4.datajabar.ui.screen.setting.SettingScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = Modifier
    ) {
        composable(BottomBarScreen.Home.route) { HomeScreen() }
        composable(BottomBarScreen.Profile.route) { ProfileScreen() }
        composable(BottomBarScreen.Settings.route) { SettingScreen() }
    }
}
