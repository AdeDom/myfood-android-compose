package com.adedom.authentication.presentation.component

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.adedom.authentication.presentation.view_model.LoginUiState
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.ui_components.theme.MyFoodTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_initialState() {
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginScreen_isErrorEmail() {
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(isErrorEmail = true),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginScreen_isErrorPassword() {
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(isErrorPassword = true),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginScreen_isLogin() {
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(isLogin = true),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginScreen_email() {
        val email = "email@gmail.com"
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(email = email),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginScreen_password() {
        val password = "password1234"
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(password = password),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginScreen_loadingDialog() {
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(dialog = LoginUiState.Dialog.Loading),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertExists()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertExists()
    }

    @Test
    fun loginScreen_errorDialog() {
        composeTestRule.setContent {
            MyFoodTheme {
                LoginContent(
                    state = LoginUiState(dialog = LoginUiState.Dialog.Error(BaseError())),
                    dispatch = {},
                    openRegisterPage = {},
                )
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertAll(hasText("Login"))
        composeTestRule.onNodeWithTag("Space column1").assertExists()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column2").assertExists()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Space column3").assertExists()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertExists()
        composeTestRule.onNode(isDialog()).assertExists()
    }
}