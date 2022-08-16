package com.adedom.myfood.presentation.component

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {

    object SplashScreen : Screen("splashScreen")

    object Welcome : Screen("welcome") {
        fun graph(): String {
            return "WelcomeGraph"
        }
    }

    object Login : Screen("login")

    object Register : Screen("register")

    object Main : Screen("main") {
        fun graph(): String {
            return "MainGraph"
        }
    }

    object FoodDetail : Screen(
        "foodDetail/{foodId}",
        listOf(navArgument("foodId") { type = NavType.IntType }),
    ) {
        fun arguments(foodId: Int): String {
            return "foodDetail/$foodId"
        }
    }
}