package com.adedom.myfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adedom.authentication.presentation.component.LoginScreen
import com.adedom.authentication.presentation.component.RegisterScreen
import com.adedom.authentication.presentation.event.LoginUiEvent
import com.adedom.authentication.presentation.event.RegisterUiEvent
import com.adedom.connectivity.component.ConnectivityCompose
import com.adedom.food_detail.presentation.component.FoodDetailScreen
import com.adedom.food_detail.presentation.event.FoodDetailUiEvent
import com.adedom.main.presentation.component.MainScreen
import com.adedom.main.presentation.event.MainUiEvent
import com.adedom.splash_screen.presentation.component.SplashScreen
import com.adedom.splash_screen.presentation.event.SplashScreenUiEvent
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.welcome.presentation.component.WelcomeScreen
import com.adedom.welcome.presentation.event.WelcomeUiEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                ConnectivityCompose()

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route,
                ) {
                    composable(Screen.SplashScreen.route) {
                        SplashScreen { uiEvent ->
                            when (uiEvent) {
                                SplashScreenUiEvent.Authentication -> {
                                    navController.navigate(Screen.Main.route) {
                                        popUpTo(Screen.SplashScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                SplashScreenUiEvent.UnAuthentication -> {
                                    navController.navigate(Screen.Welcome.route) {
                                        popUpTo(Screen.SplashScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                    }
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
                                    navController.navigate(Screen.Main.route) {
                                        popUpTo(Screen.Welcome.route) {
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
                                    navController.navigate(Screen.Main.route) {
                                        popUpTo(Screen.Welcome.route) {
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
                    composable(Screen.Main.route) {
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
        }
    }
}