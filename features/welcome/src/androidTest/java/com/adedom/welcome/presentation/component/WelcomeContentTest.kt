package com.adedom.welcome.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
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

        composeTestRule.onNodeWithTag("Welcome column root").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Welcome top section").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space welcome center").assertExists()
        composeTestRule.onNodeWithTag("Welcome bottom section").assertIsDisplayed()
    }
}