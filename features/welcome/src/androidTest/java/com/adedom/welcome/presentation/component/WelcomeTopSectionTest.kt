package com.adedom.welcome.presentation.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class WelcomeTopSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun welcomeTopSection() {
        composeTestRule.setContent {
            MyFoodTheme {
                TopSection()
            }
        }

        composeTestRule.onNodeWithContentDescription("Background logo app").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Logo app").assertIsDisplayed()
    }
}