package com.adedom.authentication.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.presentation.view_model.LoginUiEvent
import com.adedom.authentication.presentation.view_model.LoginUiState
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.authentication.utils.MainCoroutineRule
import com.adedom.core.data.Resource
import com.google.common.truth.Truth.assertThat
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.response.TokenResponse
import com.myfood.server.usecase.validate.ValidateEmailUseCase
import com.myfood.server.usecase.validate.ValidatePasswordUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val loginUseCase: LoginUseCase = mockk()
    private lateinit var validateEmailUseCase: ValidateEmailUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
        validatePasswordUseCase = ValidatePasswordUseCase()
        viewModel = LoginViewModel(
            validateEmailUseCase,
            validatePasswordUseCase,
            loginUseCase,
        )
    }

    @Test
    fun `verify login ui initial state`() {
        val state = viewModel.uiState
        assertThat(state.isErrorEmail).isFalse()
        assertThat(state.isErrorPassword).isFalse()
        assertThat(state.isLogin).isFalse()
        assertThat(state.email).isEmpty()
        assertThat(state.password).isEmpty()
        assertThat(state.dialog).isNull()
    }

    @Test
    fun `validate email on incorrect should be failed`() {
        val email = "dom"

        viewModel.dispatch(LoginUiEvent.SetEmail(email))

        val result = viewModel.uiState
        assertThat(result.email).isEqualTo(email)
        assertThat(result.password).isEqualTo("")
        assertThat(result.isErrorEmail).isTrue()
        assertThat(result.isLogin).isFalse()
    }

    @Test
    fun `validate email on correct should be success`() {
        val email = "dom6"
        val password = "1234"

        viewModel.dispatch(LoginUiEvent.SetEmail(email))
        viewModel.dispatch(LoginUiEvent.SetPassword(password))

        val result = viewModel.uiState
        assertThat(result.email).isEqualTo(email)
        assertThat(result.password).isEqualTo(password)
        assertThat(result.isErrorEmail).isFalse()
        assertThat(result.isLogin).isTrue()
    }

    @Test
    fun `validate password on incorrect should be failed`() {
        val password = "dom"

        viewModel.dispatch(LoginUiEvent.SetPassword(password))

        val result = viewModel.uiState
        assertThat(result.email).isEqualTo("")
        assertThat(result.password).isEqualTo(password)
        assertThat(result.isErrorPassword).isTrue()
        assertThat(result.isLogin).isFalse()
    }

    @Test
    fun `validate password on correct should be success`() {
        val email = "dom6"
        val password = "1234"

        viewModel.dispatch(LoginUiEvent.SetEmail(email))
        viewModel.dispatch(LoginUiEvent.SetPassword(password))

        val result = viewModel.uiState
        assertThat(result.email).isEqualTo(email)
        assertThat(result.password).isEqualTo(password)
        assertThat(result.isErrorPassword).isFalse()
        assertThat(result.isLogin).isTrue()
    }

    @Test
    fun `call service login incorrect should be return error`() = runTest {
        val email = "dom6"
        val password = "1234"
        val baseError = BaseError(message = "Email or password is incorrect")
        val resourceError = Resource.Error(baseError)
        coEvery { loginUseCase(any(), any()) } returns resourceError

        viewModel.dispatch(LoginUiEvent.SetEmail(email))
        viewModel.dispatch(LoginUiEvent.SetPassword(password))
        viewModel.dispatch(LoginUiEvent.Submit)

        val state = viewModel.uiState
        assertThat(state.dialog).isEqualTo(LoginUiState.Dialog.Error(baseError))
        assertThat(state.isLogin).isTrue()
        coVerify { loginUseCase(any(), any()) }
    }

    @Test
    fun `call service login correct should be return success`() = runTest {
        val email = "dom6"
        val password = "1234"
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        val tokenResponse = TokenResponse(accessToken, refreshToken)
        val resourceError = Resource.Success(tokenResponse)
        coEvery { loginUseCase(any(), any()) } returns resourceError

        viewModel.dispatch(LoginUiEvent.SetEmail(email))
        viewModel.dispatch(LoginUiEvent.SetPassword(password))
        viewModel.dispatch(LoginUiEvent.Submit)

        val result = viewModel.nav.firstOrNull()
        assertThat(result).isEqualTo(Unit)
        coVerify { loginUseCase(any(), any()) }
    }

    @Test
    fun `set error null should be error return null`() {
        viewModel.dispatch(LoginUiEvent.HideErrorDialog)

        val result = viewModel.uiState
        assertThat(result.dialog).isNull()
    }
}