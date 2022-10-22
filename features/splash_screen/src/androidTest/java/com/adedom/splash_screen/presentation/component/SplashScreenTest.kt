package com.adedom.splash_screen.presentation.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.adedom.ui_components.R
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun splashScreenContent() {
        composeTestRule.setContent {
            MyFoodTheme {
                SplashScreenContent()
            }
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.str_background_splash_screen))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Logo app")
            .assertIsDisplayed()
    }
}