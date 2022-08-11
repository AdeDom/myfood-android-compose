package com.adedom.splash_screen.presentation.splash_screen.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adedom.splash_screen.R
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
                    val viewModel: SplashScreenViewModel by rememberInstance()

                    val context = LocalContext.current

                    LaunchedEffect(key1 = true) {
                        viewModel.uiEvent.collect { uiEvent ->
                            when (uiEvent) {
                                SplashScreenUiEvent.Authentication -> {
                                    Toast.makeText(context, "Authentication", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                SplashScreenUiEvent.UnAuthentication -> {
                                    Toast.makeText(context, "UnAuthentication", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bg),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.Center),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_black),
                                contentDescription = null,
                                modifier = Modifier.width(200.dp),
                            )
                            Image(
                                painter = painterResource(id = R.drawable.logo_food_delivery),
                                contentDescription = null,
                                modifier = Modifier.width(120.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}