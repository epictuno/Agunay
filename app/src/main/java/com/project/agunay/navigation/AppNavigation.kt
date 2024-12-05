package com.project.agunay.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.agunay.screens.mainscreen.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) {MainScreen(navController)}
        composable(route = AppScreens.TriviaScreen.route) {}
        composable(route = AppScreens.ShopScreen.route) {}
        composable(route = AppScreens.ProfileScreen.route) {}
        composable(route = AppScreens.StepsScreen.route) {}

    }
}