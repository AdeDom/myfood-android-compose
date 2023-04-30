package com.adedom.myfood.presentation

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

        const val FOOD_ID = "foodId"

        object Init : Screen("initMain")

        object UserProfile : Screen("userProfile")

        object SearchFood : Screen("searchFood")

        object FoodDetail : Screen(
            "foodDetail/{$FOOD_ID}",
            listOf(navArgument(FOOD_ID) { type = NavType.IntType }),
        ) {
            fun arguments(foodId: Long): String {
                return "foodDetail/$foodId"
            }
        }
    }
}