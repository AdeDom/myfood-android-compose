package com.adedom.welcome.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
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

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create an Account").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don't want login?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Skip").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Border change language").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Background change language th").assertIsDisplayed()
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
        composeTestRule.onNodeWithText("EN").assertIsDisplayed()
    }
}