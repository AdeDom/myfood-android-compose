package com.adedom.splash_screen.presentation.splash_screen.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adedom.splash_screen.presentation.component.SplashScreen
import com.adedom.splash_screen.presentation.splash_screen.event.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.ui.theme.MyFoodTheme
import com.adedom.welcome.presentation.component.WelcomeScreen

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    val context = LocalContext.current

                    NavHost(
                        navController = navController,
                        startDestination = "splash_screen",
                    ) {
                        composable("splash_screen") {
                            SplashScreen { uiEvent ->
                                when (uiEvent) {
                                    SplashScreenUiEvent.Authentication -> {
                                        Toast.makeText(
                                            context,
                                            "Authentication",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    SplashScreenUiEvent.UnAuthentication -> {
                                        navController.navigate("welcome")
                                    }
                                }
                            }
                        }
                        composable("welcome") {
                            WelcomeScreen()
                        }
                        composable("main") {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}