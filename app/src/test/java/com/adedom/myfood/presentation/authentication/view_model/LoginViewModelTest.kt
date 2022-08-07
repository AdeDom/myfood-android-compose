package com.adedom.myfood.presentation.authentication.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adedom.data.utils.Resource
import com.adedom.domain.use_cases.login.LoginUseCase
import com.adedom.domain.use_cases.validate.ValidateEmailUseCase
import com.adedom.domain.use_cases.validate.ValidatePasswordUseCase
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.presentation.authentication.event.LoginUiEvent
import com.adedom.myfood.presentation.authentication.state.LoginUiState
import com.adedom.myfood.utils.MainCoroutineRule
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
    fun `set email on changed`() {
        val email = "dom@gmail.com"

        viewModel.setEmail(email)

        val result = viewModel.form.value.email
        assertThat(result).isEqualTo(email)
    }

    @Test
    fun `set password on changed`() {
        val password = "1234"

        viewModel.setPassword(password)

        val result = viewModel.form.value.password
        assertThat(result).isEqualTo(password)
    }

    @Test
    fun `validate email on incorrect should be failed`() {
        val email = "dom"
        viewModel.setEmail(email)

        viewModel.onValidateEmail()

        val result = viewModel.uiState.value as LoginUiState.ValidateEmail
        assertThat(result.isError).isTrue()
        assertThat(result.isLogin).isFalse()
    }

    @Test
    fun `validate email on correct should be success`() {
        val email = "dom6"
        val password = "1234"
        viewModel.setEmail(email)
        viewModel.setPassword(password)

        viewModel.onValidateEmail()

        val result = viewModel.uiState.value as LoginUiState.ValidateEmail
        assertThat(result.isError).isFalse()
        assertThat(result.isLogin).isTrue()
    }

    @Test
    fun `validate password on incorrect should be failed`() {
        val password = "dom"
        viewModel.setPassword(password)

        viewModel.onValidatePassword()

        val result = viewModel.uiState.value as LoginUiState.ValidatePassword
        assertThat(result.isError).isTrue()
        assertThat(result.isLogin).isFalse()
    }

    @Test
    fun `validate password on correct should be success`() {
        val email = "dom6"
        val password = "1234"
        viewModel.setEmail(email)
        viewModel.setPassword(password)

        viewModel.onValidatePassword()

        val result = viewModel.uiState.value as LoginUiState.ValidatePassword
        assertThat(result.isError).isFalse()
        assertThat(result.isLogin).isTrue()
    }

    @Test
    fun `call service login incorrect should be return error`() = runTest {
        val email = "dom6"
        val password = "1234"
        viewModel.setEmail(email)
        viewModel.setPassword(password)
        val baseError = BaseError(message = "Email or password is incorrect")
        val resourceError = Resource.Error(baseError)
        coEvery { loginUseCase(any(), any()) } returns resourceError

        viewModel.onLoginEvent()

        val result = viewModel.uiState.value as LoginUiState.LoginError
        assertThat(result).isEqualTo(LoginUiState.LoginError(baseError))
        coVerify { loginUseCase(any(), any()) }
    }

    @Test
    fun `call service login correct should be return success`() = runTest {
        val email = "dom6"
        val password = "1234"
        viewModel.setEmail(email)
        viewModel.setPassword(password)
        val resourceError = Resource.Success(Unit)
        coEvery { loginUseCase(any(), any()) } returns resourceError

        launch {
            viewModel.onLoginEvent()
        }

        val result = viewModel.uiEvent.firstOrNull()
        assertThat(result).isEqualTo(LoginUiEvent.LoginSuccess)
        coVerify { loginUseCase(any(), any()) }
    }

    @Test
    fun `on click register event`() = runTest {
        launch {
            viewModel.onRegisterEvent()
        }

        val result = viewModel.uiEvent.firstOrNull()
        assertThat(result).isEqualTo(LoginUiEvent.Register)
    }
}