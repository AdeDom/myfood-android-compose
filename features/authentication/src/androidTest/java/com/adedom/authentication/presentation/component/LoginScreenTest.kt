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
    fun loginScreen_initialState() {
        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldEmail_performTextInputEmailIncorrect_returnErrorEmail() {
        val email = "ema"

        composeTestRule.onNodeWithText("Your Email").performTextInput(email)

        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assert(hasImeAction(ImeAction.Next))
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldEmail_performTextInputEmailCorrect_returnEmail() {
        val email = "email@gmail.com"

        composeTestRule.onNodeWithText("Your Email").performTextInput(email)

        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText("Your Email").assert(hasImeAction(ImeAction.Next))
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldPassword_performTextInputPasswordIncorrect_returnErrorPassword() {
        val password = "pas"

        composeTestRule.onNodeWithText("Password").performTextInput(password)

        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assert(hasImeAction(ImeAction.Done))
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldPassword_performTextInputPasswordCorrect_returnPassword() {
        val password = "password1234"

        composeTestRule.onNodeWithText("Password").performTextInput(password)

        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assert(hasImeAction(ImeAction.Done))
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsNotEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginSubmit_incorrect_returnError() {
        val email = "email@gmail.com"
        val password = "password1234"
        val resourceError = Resource.Error(BaseError())
        coEvery { loginUseCase(any(), any()) } returns resourceError

        composeTestRule.onNodeWithText("Your Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password").performTextInput(password)
        composeTestRule.onAllNodesWithText("Login")[1].performClick()

        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertExists()
        composeTestRule.onNode(isDialog()).assertExists()
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
        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertExists()
        composeTestRule.onNode(isDialog()).assertExists()

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onAllNodesWithText("Login")[0].assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your details to login").assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email is incorrect").assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password is incorrect").assertDoesNotExist()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Login")[1].assertIsEnabled()
        composeTestRule.onNodeWithText("Forget your password?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don\'t have an Account?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }
}