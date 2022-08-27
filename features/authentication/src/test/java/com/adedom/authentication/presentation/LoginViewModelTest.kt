package com.adedom.authentication.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adedom.authentication.domain.use_cases.LoginUseCase
import com.adedom.authentication.domain.use_cases.ValidateEmailUseCase
import com.adedom.authentication.domain.use_cases.ValidatePasswordUseCase
import com.adedom.authentication.presentation.view_model.LoginUiAction
import com.adedom.authentication.presentation.view_model.LoginUiEvent
import com.adedom.authentication.presentation.view_model.LoginViewModel
import com.adedom.authentication.utils.MainCoroutineRule
import com.adedom.core.utils.Resource
import com.adedom.myfood.data.models.base.BaseError
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
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
    fun `validate email on incorrect should be failed`() {
        val email = "dom"

        viewModel.dispatch(LoginUiAction.SetEmail(email))

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

        viewModel.dispatch(LoginUiAction.SetEmail(email))
        viewModel.dispatch(LoginUiAction.SetPassword(password))

        val result = viewModel.uiState
        assertThat(result.email).isEqualTo(email)
        assertThat(result.password).isEqualTo(password)
        assertThat(result.isErrorEmail).isFalse()
        assertThat(result.isLogin).isTrue()
    }

    @Test
    fun `validate password on incorrect should be failed`() {
        val password = "dom"

        viewModel.dispatch(LoginUiAction.SetPassword(password))

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

        viewModel.dispatch(LoginUiAction.SetEmail(email))
        viewModel.dispatch(LoginUiAction.SetPassword(password))

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

        viewModel.dispatch(LoginUiAction.SetEmail(email))
        viewModel.dispatch(LoginUiAction.SetPassword(password))
        viewModel.dispatch(LoginUiAction.Submit)

        val state = viewModel.uiState
        assertThat(state.error).isEqualTo(baseError)
        assertThat(state.isLoading).isFalse()
        assertThat(state.isLogin).isTrue()
        coVerify { loginUseCase(any(), any()) }
    }

    @Test
    fun `call service login correct should be return success`() = runTest {
        val email = "dom6"
        val password = "1234"
        val resourceError = Resource.Success(Unit)
        coEvery { loginUseCase(any(), any()) } returns resourceError

        launch {
            viewModel.dispatch(LoginUiAction.SetEmail(email))
            viewModel.dispatch(LoginUiAction.SetPassword(password))
            viewModel.dispatch(LoginUiAction.Submit)
        }

        val result = viewModel.uiEvent.firstOrNull()
        assertThat(result).isEqualTo(LoginUiEvent.NavMain)
        coVerify { loginUseCase(any(), any()) }
    }

    @Test
    fun `set error null should be error return null`() {
        viewModel.dispatch(LoginUiAction.HideErrorDialog)

        val result = viewModel.uiState
        assertThat(result.error).isNull()
    }

    @Test
    fun `on click register event`() = runTest {
        launch {
            viewModel.dispatch(LoginUiAction.NavRegister)
        }

        val result = viewModel.uiEvent.firstOrNull()
        assertThat(result).isEqualTo(LoginUiEvent.NavRegister)
    }
}