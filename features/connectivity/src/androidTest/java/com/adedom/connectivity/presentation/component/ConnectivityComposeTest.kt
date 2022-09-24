package com.adedom.connectivity.presentation.component

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.adedom.connectivity.data.models.Status
import com.adedom.connectivity.presentation.view_model.ConnectivityUiState
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class ConnectivityComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun connectivityContent_statusAvailable_returnOnlineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Available,
                    ),
                    dispatch = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithTag("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText("Online").assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusUnavailable_returnOfflineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Unavailable,
                    ),
                    dispatch = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithTag("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText("Offline").assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusLosing_returnOfflineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Losing,
                    ),
                    dispatch = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithTag("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText("Offline").assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusLost_returnOfflineNetworkPopup() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Lost,
                    ),
                    dispatch = {},
                )
            }
        }

        composeTestRule.onNode(isPopup()).assertIsDisplayed()
        composeTestRule.onNodeWithTag("Background network popup").assertIsDisplayed()
        composeTestRule.onNodeWithText("Offline").assertIsDisplayed()
    }

    @Test
    fun connectivityContent_statusUnknown_returnEmptyRoot() {
        composeTestRule.setContent {
            MyFoodTheme {
                ConnectivityContent(
                    state = ConnectivityUiState(
                        status = Status.Unknown,
                    ),
                    dispatch = {},
                )
            }
        }

        composeTestRule.onRoot().assertIsNotDisplayed()
    }
}