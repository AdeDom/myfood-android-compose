package com.adedom.myfood.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.adedom.authentication.presentation.component.LoginScreen
import com.adedom.authentication.presentation.component.RegisterScreen
import com.adedom.authentication.presentation.event.LoginUiEvent
import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.food_detail.presentation.component.FoodDetailScreen
import com.adedom.food_detail.presentation.event.FoodDetailUiEvent
import com.adedom.main.presentation.component.MainScreen
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.splash_screen.presentation.component.SplashScreen
import com.adedom.splash_screen.presentation.event.SplashScreenUiEvent
import com.adedom.welcome.presentation.component.WelcomeScreen
import com.adedom.welcome.presentation.event.WelcomeUiEvent

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
                    SplashScreenUiEvent.Authentication -> {
                        navController.navigate(Screen.Main.graph()) {
                            popUpTo(Screen.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                    SplashScreenUiEvent.UnAuthentication -> {
                        navController.navigate(Screen.Welcome.graph()) {
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
    navigation(startDestination = Screen.Welcome.route, route = Screen.Welcome.graph()) {
        composable(Screen.Welcome.route) {
            WelcomeScreen { uiEvent ->
                when (uiEvent) {
                    WelcomeUiEvent.Login -> {
                        navController.navigate(Screen.Login.route)
                    }
                    WelcomeUiEvent.Register -> {
                        navController.navigate(Screen.Register.route)
                    }
                    is WelcomeUiEvent.Skip -> {
                        navController.navigate(Screen.Main.graph()) {
                            popUpTo(Screen.Welcome.graph()) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
        composable(Screen.Login.route) {
            LoginScreen { uiEvent ->
                when (uiEvent) {
                    LoginUiEvent.LoginSuccess -> {
                        navController.navigate(Screen.Main.graph()) {
                            popUpTo(Screen.Welcome.graph()) {
                                inclusive = true
                            }
                        }
                    }
                    LoginUiEvent.Register -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Register.route)
                    }
                }
            }
        }
        composable(Screen.Register.route) {
            RegisterScreen { uiEvent ->
                when (uiEvent) {
                    RegisterUiEvent.Login -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Login.route)
                    }
                    RegisterUiEvent.Register -> {}
                }
            }
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(startDestination = Screen.Main.route, route = Screen.Main.graph()) {
        composable(Screen.Main.route) {
            MainScreen { uiEvent ->
                when (uiEvent) {
                    MainUiEvent.Logout -> {
                        navController.navigate(Screen.Welcome.graph()) {
                            popUpTo(Screen.Main.graph()) {
                                inclusive = true
                            }
                        }
                    }
                    is MainUiEvent.FoodDetail -> {
                        navController.navigate(Screen.FoodDetail.arguments(uiEvent.foodId))
                    }
                }
            }
        }
        composable(
            route = Screen.FoodDetail.route,
            arguments = Screen.FoodDetail.arguments,
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