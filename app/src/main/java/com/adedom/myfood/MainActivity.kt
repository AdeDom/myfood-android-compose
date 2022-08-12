package com.adedom.myfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adedom.main.presentation.component.MainScreen
import com.adedom.myfood.ui.theme.MyFoodTheme
import com.adedom.splash_screen.presentation.component.SplashScreen
import com.adedom.splash_screen.presentation.event.SplashScreenUiEvent
import com.adedom.welcome.presentation.component.WelcomeScreen
import com.adedom.welcome.presentation.event.WelcomeUiEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route,
                    ) {
                        composable(Screen.SplashScreen.route) {
                            SplashScreen { uiEvent ->
                                when (uiEvent) {
                                    SplashScreenUiEvent.Authentication -> {
                                        navController.popBackStack()
                                        navController.navigate(Screen.Main.route)
                                    }
                                    SplashScreenUiEvent.UnAuthentication -> {
                                        navController.popBackStack()
                                        navController.navigate(Screen.Welcome.route)
                                    }
                                }
                            }
                        }
                        composable(Screen.Welcome.route) {
                            WelcomeScreen { uiEvent ->
                                when (uiEvent) {
                                    WelcomeUiEvent.Login -> {}
                                    WelcomeUiEvent.Register -> {}
                                    is WelcomeUiEvent.Skip -> {}
                                }
                            }
                        }
                        composable(Screen.Main.route) {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}