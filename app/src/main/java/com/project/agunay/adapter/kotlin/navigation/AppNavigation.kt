package com.project.agunay.adapter.kotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.adapter.kotlin.screens.mainscreen.MainScreen
import com.project.agunay.adapter.kotlin.screens.registerScreen.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val CurrentUser = CurrentUser()
    NavHost(navController = navController, startDestination = AppScreens.RegisterScreen.route) {
        composable(route = AppScreens.RegisterScreen.route){backStackEntry ->
            RegisterScreen(navController, CurrentUser, backStackEntry)
        }
        composable(route = AppScreens.MainScreen.route) { MainScreen(navController) }
        composable(route = AppScreens.TriviaScreen.route) {}
        composable(route = AppScreens.ShopScreen.route) {}
        composable(route = AppScreens.ProfileScreen.route) {}
        composable(route = AppScreens.StepsScreen.route) {}

    }
}