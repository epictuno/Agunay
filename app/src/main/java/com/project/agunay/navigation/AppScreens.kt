package com.project.agunay.navigation

sealed class AppScreens(val route: String) {
    object MainScreen : AppScreens("main_screen")
    object TriviaScreen: AppScreens("trivia_screen")
    object StepsScreen: AppScreens("steps_screen")
    object ShopScreen: AppScreens("shop_screen")
    object ProfileScreen: AppScreens("profile_screen")
}