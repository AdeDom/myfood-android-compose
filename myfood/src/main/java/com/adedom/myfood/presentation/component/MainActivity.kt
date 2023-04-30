package com.adedom.myfood.presentation.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.adedom.connectivity.presentation.component.ConnectivityScreen
import com.adedom.ui_components.theme.MyFoodTheme
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                ConnectivityScreen(koinViewModel())
                MainAppNavHost()
            }
        }
    }
}