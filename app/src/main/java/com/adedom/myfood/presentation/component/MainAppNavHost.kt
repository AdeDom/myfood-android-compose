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
                    WelcomeUiEvent.Login -> {
                        navController.navigate(Screen.Welcome.Login.route)
                    }
                    WelcomeUiEvent.Register -> {
                        navController.navigate(Screen.Welcome.Register.route)
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
        composable(Screen.Welcome.Login.route) {
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
                        navController.navigate(Screen.Welcome.Register.route)
                    }
                }
            }
        }
        composable(Screen.Welcome.Register.route) {
            RegisterScreen { uiEvent ->
                when (uiEvent) {
                    RegisterUiEvent.Login -> {
                        navController.popBackStack()
                        navController.navigate(Screen.Welcome.Login.route)
                    }
                    RegisterUiEvent.Register -> {}
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
        var mainSaveState: MainUiEvent.SaveState? = null
        composable(Screen.Main.Init.route) {
            MainScreen(mainSaveState) { uiEvent ->
                when (uiEvent) {
                    MainUiEvent.Logout -> {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                    is MainUiEvent.FoodDetail -> {
                        navController.navigate(Screen.Main.FoodDetail.arguments(uiEvent.foodId))
                    }
                    is MainUiEvent.SaveState -> {
                        mainSaveState = uiEvent
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