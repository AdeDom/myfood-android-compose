package com.adedom.connectivity.presentation.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isPopup
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.presentation.view_model.ConnectivityUiState
import com.adedom.ui.components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test
import com.adedom.ui.components.R as res

class ConnectivityComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun connectivityContent_statusAvailable_returnOnlineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Available,
                    ),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_online))
            .assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusUnavailable_returnOfflineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Unavailable,
                    ),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_offline))
            .assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusLosing_returnOfflineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Losing,
                    ),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_offline))
            .assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusLost_returnOfflineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Lost,
                    ),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_offline))
            .assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusUnknown_returnEmptyRoot() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Unknown,
                    ),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onRoot().assertIsNotDisplayed()
    }
}