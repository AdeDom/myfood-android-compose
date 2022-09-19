package com.adedom.splash_screen.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreenContent() {
        composeTestRule.setContent {
            MyFoodTheme {
                SplashScreenContent()
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Background splash screen")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Logo app")
            .assertIsDisplayed()
    }
}