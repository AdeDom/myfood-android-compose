package com.adedom.authentication.presentation.component

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.input.ImeAction
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.core.data.Resource
import com.adedom.ui_components.theme.MyFoodTheme
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.usecase.validate.ValidateEmailUseCase
import com.myfood.server.usecase.validate.ValidatePasswordUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var validateEmailUseCase: ValidateEmailUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private val loginUseCase: LoginUseCase = mockk()
    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
        validatePasswordUseCase = ValidatePasswordUseCase()
        viewModel = LoginViewModel(
            validateEmailUseCase,
            validatePasswordUseCase,
            loginUseCase,
        )

        composeTestRule.setContent {
            MyFoodTheme {
                LoginScreen(
                    viewModel = viewModel,
                    openRegisterPage = {},
                    openMainPage = {},
                )
            }
        }
    }

    @Test
    fun textFieldEmail_incorrect_returnErrorEmail() {
        val email = "dom"
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()

        composeTestRule.onNodeWithText("Your Email").performTextInput(email)

        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Your Email").assert(hasImeAction(ImeAction.Next))
    }

    @Test
    fun textFieldEmail_correct_returnEmail() {
        val email = "dom6"
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()

        composeTestRule.onNodeWithText("Your Email").performTextInput(email)

        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Your Email").assert(hasImeAction(ImeAction.Next))
    }

    @Test
    fun textFieldPassword_incorrect_returnErrorPassword() {
        val password = "dom"
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()

        composeTestRule.onNodeWithText("Password").performTextInput(password)

        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Password").assert(hasImeAction(ImeAction.Done))
    }

    @Test
    fun textFieldPassword_correct_returnPassword() {
        val password = "dom6"
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()

        composeTestRule.onNodeWithText("Password").performTextInput(password)

        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Password").assert(hasImeAction(ImeAction.Done))
    }

    @Test
    fun loginSubmit_incorrect_returnError() {
        val email = "email@gmail.com"
        val password = "password1234"
        val resourceError = Resource.Error(BaseError())
        coEvery { loginUseCase(any(), any()) } returns resourceError
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()

        composeTestRule.onNodeWithText("Your Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password").performTextInput(password)
        composeTestRule.onAllNodesWithText("Login")[1].performClick()

        composeTestRule.onNode(isDialog()).assertExists()
        composeTestRule.onNodeWithTag("Error dialog").assertExists()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsEnabled()
    }

    @Test
    fun loginError_onClick_returnHideDialog() {
        val email = "email@gmail.com"
        val password = "password1234"
        val resourceError = Resource.Error(BaseError())
        coEvery { loginUseCase(any(), any()) } returns resourceError
        composeTestRule.onNodeWithText("Your Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password").performTextInput(password)
        composeTestRule.onAllNodesWithText("Login")[1].performClick()
        composeTestRule.onNode(isDialog()).assertExists()
        composeTestRule.onNodeWithTag("Error dialog").assertExists()

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNode(isDialog()).assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
    }
}