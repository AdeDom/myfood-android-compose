package com.adedom.myfood.presentation.component

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.adedom.authentication.presentation.component.LoginScreen
import com.adedom.authentication.presentation.component.RegisterScreen
import com.adedom.authentication.presentation.view_model.LoginUiEvent
import com.adedom.authentication.presentation.view_model.RegisterUiEvent
import com.adedom.food_detail.presentation.component.FoodDetailScreen
import com.adedom.food_detail.presentation.view_model.FoodDetailUiEvent
import com.adedom.main.presentation.component.MainScreen
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.search_food.presentation.component.SearchFoodScreen
import com.adedom.search_food.presentation.event.SearchFoodUiEvent
import com.adedom.splash_screen.presentation.component.SplashScreen
import com.adedom.splash_screen.presentation.view_model.SplashScreenUiEvent
import com.adedom.welcome.presentation.component.WelcomeScreen
import com.adedom.welcome.presentation.view_model.WelcomeUiEvent

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.SplashScreen.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen { uiEvent ->
                when (uiEvent) {
                    SplashScreenUiEvent.NavMain -> {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                    SplashScreenUiEvent.NavWelcome -> {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
        authGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Welcome.Init.route,
        route = Screen.Welcome.route,
    ) {
        composable(Screen.Welcome.Init.route) {
            WelcomeScreen { uiEvent ->
                when (uiEvent) {
                    WelcomeUiEvent.NavLogin -> {
                        navController.navigate(Screen.Welcome.Login.route)
                    }
                    WelcomeUiEvent.NavRegister -> {
                        navController.navigate(Screen.Welcome.Register.route)
                    }
                    is WelcomeUiEvent.NavSkip -> {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Welcome.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
        composable(Screen.Welcome.Login.route) {
            LoginScreen { uiEvent ->
                when (uiEvent) {
                    LoginUiEvent.NavMain -> {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Welcome.route) {
                                inclusive = true
                            }
                        }
                    }
                    LoginUiEvent.NavRegister -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Welcome.Register.route)
                    }
                }
            }
        }
        composable(Screen.Welcome.Register.route) {
            RegisterScreen { uiEvent ->
                when (uiEvent) {
                    RegisterUiEvent.NavLogin -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Welcome.Login.route)
                    }
                    RegisterUiEvent.NavMain -> {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Welcome.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Main.Init.route,
        route = Screen.Main.route,
    ) {
        composable(Screen.Main.Init.route) {
            val context = LocalContext.current
            MainScreen { uiEvent ->
                when (uiEvent) {
                    MainUiEvent.Logout -> {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                    is MainUiEvent.FoodDetail -> {
                        val route = Screen.Main.FoodDetail.arguments(uiEvent.foodId)
                        navController.navigate(route)
                    }
                    MainUiEvent.SearchFood -> {
                        navController.navigate(Screen.Main.SearchFood.route)
                    }
                    MainUiEvent.OnBackAlert -> {
                        Toast.makeText(context, "Tap again to exit the app", Toast.LENGTH_SHORT).show()
                    }
                    MainUiEvent.OnBackPressed -> {
                        (context as? Activity)?.finishAffinity()
                    }
                }
            }
        }
        composable(Screen.Main.SearchFood.route) {
            SearchFoodScreen { uiEvent ->
                when (uiEvent) {
                    is SearchFoodUiEvent.FoodDetail -> {
                        val route = Screen.Main.FoodDetail.arguments(uiEvent.foodId)
                        navController.navigate(route)
                    }
                    SearchFoodUiEvent.OnBackPressed -> {
                        navController.popBackStack()
                    }
                }
            }
        }
        composable(
            route = Screen.Main.FoodDetail.route,
            arguments = Screen.Main.FoodDetail.arguments,
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId")
            FoodDetailScreen(foodId) { uiEvent ->
                when (uiEvent) {
                    FoodDetailUiEvent.OnBackPressed -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}