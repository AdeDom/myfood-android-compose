package com.adedom.myfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adedom.connectivity.presentation.component.ConnectivityScreen
import com.adedom.myfood.presentation.component.MainAppNavHost
import com.adedom.ui_components.theme.MyFoodTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                ConnectivityScreen(getViewModel())
                MainAppNavHost()
            }
        }
    }
}