package com.adedom.welcome.presentation.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test
import com.adedom.ui_components.R as res

class WelcomeBottomSectionTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun welcomeBottomSection() {
        composeTestRule.setContent {
            MyFoodTheme {
                WelcomeBottomSection(
                    openLoginPage = {},
                    openRegisterPage = {},
                    openHomePage = {},
                )
            }
        }

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_create_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_want_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_skip))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Border change language").assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Background change language th")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_th))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_en))
            .assertIsDisplayed()
    }
}