package com.adedom.splash_screen.presentation.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.adedom.ui.components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test
import com.adedom.ui.components.R as res

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
            .onNodeWithContentDescription(composeTestRule.activity.getString(res.string.str_background_splash_screen))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.getString(res.string.cd_logo_app))
            .assertIsDisplayed()
    }
}