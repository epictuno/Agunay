package com.project.agunay.adapter.kotlin.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.agunay.adapter.kotlin.configuration.CurrentQuizz
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.adapter.kotlin.screens.loginScreen.LoginScreen
import com.project.agunay.adapter.kotlin.screens.mainscreen.MainScreen
import com.project.agunay.adapter.kotlin.screens.profilescreen.ProfileScreen
import com.project.agunay.adapter.kotlin.screens.registerScreen.RegisterScreen
import com.project.agunay.adapter.kotlin.screens.shopscreen.ShopScreen
import com.project.agunay.adapter.kotlin.screens.stepscreen.StepScreen
import com.project.agunay.adapter.kotlin.screens.triviascreen.TriviaScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentUser = CurrentUser()
    val currentQuizz = CurrentQuizz()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route){backStackEntry ->
            LoginScreen(navController, currentUser, backStackEntry)
        }
        composable(route = AppScreens.RegisterScreen.route){backStackEntry ->
            RegisterScreen(navController, currentUser, backStackEntry)
        }
        composable(route = AppScreens.MainScreen.route) {backStackEntry ->
            MainScreen(navController, currentUser,currentQuizz, backStackEntry)
        }
        composable(route = AppScreens.TriviaScreen.route) {backStackEntry ->
            Log.d("FirebaseQuizzRepository", "Quizz details: " + (currentQuizz.getQuizz()?.questions
                ?: "NO CURRENT QUIZZ"))
            TriviaScreen(navController, currentQuizz, currentUser, backStackEntry) }
        composable(route = AppScreens.ShopScreen.route) { backStackEntry ->
            ShopScreen(navController, currentUser, backStackEntry) }
        composable(route = AppScreens.ProfileScreen.route) {backStackEntry ->
            ProfileScreen(navController, currentUser, backStackEntry)
        }
        composable(route = AppScreens.StepsScreen.route) { backStackEntry ->
            StepScreen(navController, currentUser, backStackEntry)
        }

    }
}