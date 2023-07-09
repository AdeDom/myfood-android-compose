package com.adedom.welcome.presentation.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.adedom.ui.components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test
import com.adedom.ui.components.R as res

class WelcomeTopSectionTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun welcomeTopSection() {
        composeTestRule.setContent {
            MyFoodTheme {
                WelcomeTopSection()
            }
        }

        composeTestRule.onNodeWithContentDescription("Background logo app").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(res.string.cd_logo_app))
            .assertIsDisplayed()
    }
}