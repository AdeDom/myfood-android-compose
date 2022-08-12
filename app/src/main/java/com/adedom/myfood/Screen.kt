package com.adedom.myfood

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
}