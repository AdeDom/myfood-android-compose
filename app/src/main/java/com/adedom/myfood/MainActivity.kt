package com.adedom.myfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adedom.connectivity.presentation.component.ConnectivityCompose
import com.adedom.myfood.presentation.component.MainAppNavHost
import com.adedom.ui_components.theme.MyFoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                ConnectivityCompose()
                MainAppNavHost()
            }
        }
    }
}