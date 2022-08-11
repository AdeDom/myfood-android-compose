package com.adedom.splash_screen.presentation.splash_screen.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.adedom.splash_screen.presentation.splash_screen.event.SplashScreenUiEvent
import com.adedom.splash_screen.presentation.splash_screen.view_model.SplashScreenViewModel
import com.adedom.splash_screen.presentation.ui.theme.MyFoodTheme
import org.kodein.di.compose.rememberInstance

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val viewModel: SplashScreenViewModel by rememberInstance()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                SplashScreenUiEvent.Authentication -> {
                    Toast.makeText(context, "Authentication", Toast.LENGTH_SHORT).show()
                }
                SplashScreenUiEvent.UnAuthentication -> {
                    Toast.makeText(context, "UnAuthentication", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Text(text = "Hello $name!")
}