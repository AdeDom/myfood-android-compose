package com.adedom.splash_screen.presentation.screen

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object Welcome : Screen("welcome")
    object Main : Screen("main")
}