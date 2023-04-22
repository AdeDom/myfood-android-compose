package com.adedom.welcome.presentation.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class WelcomeContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun welcomeContent() {
        composeTestRule.setContent {
            MyFoodTheme {
                WelcomeContent(
                    onEvent = {},
                    openLoginPage = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Welcome top section").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Welcome bottom section").assertIsDisplayed()
    }
}