package com.adedom.welcome.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class WelcomeBottomSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun welcomeBottomSection() {
        composeTestRule.setContent {
            MyFoodTheme {
                BottomSection(
                    dispatch = {},
                    openLoginPage = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("Welcome bottom section root column").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Login button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithTag("Create an account button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithTag("Don't want login? skip test").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithTag("Row change language").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Box change language").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Text th").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Box en").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Text en").assertIsDisplayed()
    }
}