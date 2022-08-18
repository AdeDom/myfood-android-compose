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

        object Init : Screen("initWelcome")

        object Login : Screen("login")

        object Register : Screen("register")
    }

    object Main : Screen("main") {

        object Init : Screen("initMain")

        object FoodDetail : Screen(
            "foodDetail/{foodId}",
            listOf(navArgument("foodId") { type = NavType.IntType }),
        ) {
            fun arguments(foodId: Long): String {
                return "foodDetail/$foodId"
            }
        }
    }
}