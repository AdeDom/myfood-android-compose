package com.adedom.welcome.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class WelcomeContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun welcomeContent() {
        composeTestRule.setContent {
            MyFoodTheme {
                WelcomeContent(
                    dispatch = {},
                    openLoginPage = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Welcome top section").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Welcome bottom section").assertIsDisplayed()
    }
}