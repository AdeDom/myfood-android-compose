package com.adedom.authentication.presentation.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.ImeAction
import com.adedom.authentication.domain.use_cases.FavoriteUseCase
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.core.utils.ApiServiceException
import com.adedom.ui.components.theme.MyFoodTheme
import com.adedom.user_profile.domain.use_cases.FetchUserProfileUseCase
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.usecase.validate.ValidateEmailUseCase
import com.myfood.server.usecase.validate.ValidatePasswordUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.adedom.ui.components.R as res

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var validateEmailUseCase: ValidateEmailUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private val loginUseCase: LoginUseCase = mockk()
    private val fetchUserProfileUseCase: FetchUserProfileUseCase = mockk()
    private val favoriteUseCase: FavoriteUseCase = mockk()
    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
        validatePasswordUseCase = ValidatePasswordUseCase()
        viewModel = LoginViewModel(
            validateEmailUseCase,
            validatePasswordUseCase,
            loginUseCase,
            fetchUserProfileUseCase,
            favoriteUseCase,
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
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsNotEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldEmail_performTextInputEmailIncorrect_returnErrorEmail() {
        val email = "ema"

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .performTextInput(email)

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assert(hasImeAction(ImeAction.Next))
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsNotEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldEmail_performTextInputEmailCorrect_returnEmail() {
        val email = "email@gmail.com"

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .performTextInput(email)

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assert(hasImeAction(ImeAction.Next))
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsNotEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldPassword_performTextInputPasswordIncorrect_returnErrorPassword() {
        val password = "pas"

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .performTextInput(password)

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assert(hasImeAction(ImeAction.Done))
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsNotEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun textFieldPassword_performTextInputPasswordCorrect_returnPassword() {
        val password = "password1234"

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .performTextInput(password)

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assert(hasImeAction(ImeAction.Done))
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsNotEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
    }

    @Test
    fun loginSubmit_correct_returnLastState() {
        val email = "email@gmail.com"
        val password = "password1234"
        val messageError = "Email or password incorrect."
        coEvery { loginUseCase(any(), any()) } returns Unit
        coEvery { fetchUserProfileUseCase() } returns Unit
        coEvery { favoriteUseCase() } returns Unit

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .performTextInput(email)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .performTextInput(password)
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .performClick()

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsNotEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertExists()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertDoesNotExist()
        composeTestRule.onNodeWithText(messageError).assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertExists()
        coVerify { loginUseCase(any(), any()) }
        coVerify { fetchUserProfileUseCase() }
        coVerify { favoriteUseCase() }
    }

    @Test
    fun loginSubmit_incorrect_returnError() {
        val email = "email@gmail.com"
        val password = "password1234"
        val messageError = "Email or password incorrect."
        val baseError = BaseError(message = messageError)
        val exception = ApiServiceException(baseError)
        coEvery { loginUseCase(any(), any()) } throws exception
        coEvery { fetchUserProfileUseCase() } returns Unit
        coEvery { favoriteUseCase() } returns Unit

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .performTextInput(email)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .performTextInput(password)
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .performClick()

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertExists()
        composeTestRule.onNodeWithText(messageError).assertIsDisplayed()
        composeTestRule.onNode(isDialog()).assertExists()
        coVerify { loginUseCase(any(), any()) }
        coVerify(exactly = 0) { fetchUserProfileUseCase() }
        coVerify(exactly = 0) { favoriteUseCase() }
    }

    @Test
    fun loginSubmit_correctTokenIsNull_returnError() {
        val email = "email@gmail.com"
        val password = "password1234"
        val messageError = "Email or password incorrect."
        val exception = Throwable(messageError)
        coEvery { loginUseCase(any(), any()) } throws exception
        coEvery { fetchUserProfileUseCase() } returns Unit
        coEvery { favoriteUseCase() } returns Unit

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .performTextInput(email)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .performTextInput(password)
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .performClick()

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertExists()
        composeTestRule.onNodeWithText(messageError).assertIsDisplayed()
        composeTestRule.onNode(isDialog()).assertExists()
        coVerify { loginUseCase(any(), any()) }
        coVerify(exactly = 0) { fetchUserProfileUseCase() }
        coVerify(exactly = 0) { favoriteUseCase() }
    }

    @Test
    fun loginError_onClick_returnHideDialog() {
        val email = "email@gmail.com"
        val password = "password1234"
        val messageError = "Email or password incorrect."
        val baseError = BaseError(message = messageError)
        val exception = ApiServiceException(baseError)
        coEvery { loginUseCase(any(), any()) } throws exception
        coEvery { fetchUserProfileUseCase() } returns Unit
        coEvery { favoriteUseCase() } returns Unit
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .performTextInput(email)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .performTextInput(password)
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .performClick()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertExists()
        composeTestRule.onNodeWithText(messageError).assertIsDisplayed()
        composeTestRule.onNode(isDialog()).assertExists()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_ok))
            .performClick()

        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[0]
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_add_your_details_to_login))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_your_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_email_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(password).assertDoesNotExist()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_password_is_incorrect))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(composeTestRule.activity.getString(res.string.str_login))[1]
            .assertIsEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_forget_your_password))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_don_t_have_an_account))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(res.string.str_sign_up))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading dialog").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Error dialog").assertDoesNotExist()
        composeTestRule.onNodeWithText(messageError).assertDoesNotExist()
        composeTestRule.onNode(isDialog()).assertDoesNotExist()
        coVerify { loginUseCase(any(), any()) }
        coVerify(exactly = 0) { fetchUserProfileUseCase() }
        coVerify(exactly = 0) { favoriteUseCase() }
    }
}