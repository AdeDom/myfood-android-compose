package com.adedom.showkase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adedom.splash_screen.presentation.component.SplashScreenContent
import com.adedom.ui_components.theme.MyFoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                SplashScreenContent()
            }
        }
    }
}