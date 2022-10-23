package com.adedom.authentication.domain

import com.adedom.authentication.data.providers.data_store.FakeAppDataStore
import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.data.repositories.AuthRepositoryImpl
import com.adedom.authentication.domain.repositories.AuthRepository
import com.adedom.authentication.domain.use_cases.RegisterUseCase
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.google.common.truth.Truth.assertThat
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.TokenResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest {

    private val authRemoteDataSource: AuthRemoteDataSource = mockk()
    private lateinit var appDataStore: AppDataStore
    private lateinit var authRepository: AuthRepository
    private lateinit var useCase: RegisterUseCase

    @Before
    fun setUp() {
        appDataStore = FakeAppDataStore()
        authRepository = AuthRepositoryImpl(
            appDataStore,
            authRemoteDataSource,
        )
        useCase = RegisterUseCase(
            authRepository,
        )
    }

    @Test(expected = ApiServiceException::class)
    fun `login incorrect return error`() = runTest {
        val email = "example@gmail.com"
        val password = "1234"
        val name = "Android"
        val mobileNo = null
        val address = null
        val messageError = "Api error."
        val baseError = BaseError(message = messageError)
        val exception = ApiServiceException(baseError)
        coEvery { authRemoteDataSource.callRegister(any()) } throws exception

        useCase(email, password, name, mobileNo, address)

        assertThat(appDataStore.getAccessToken()).isNull()
        assertThat(appDataStore.getRefreshToken()).isNull()
        assertThat(authRepository.getAuthRole()).isEqualTo(AuthRole.Unknown)
        coEvery { authRemoteDataSource.callRegister(any()) }
    }

    @Test(expected = Throwable::class)
    fun `login correct token is null return error`() = runTest {
        val email = "example@gmail.com"
        val password = "1234"
        val name = "Android"
        val mobileNo = null
        val address = null
        coEvery { authRemoteDataSource.callRegister(any()) } returns BaseResponse()

        useCase(email, password, name, mobileNo, address)

        assertThat(appDataStore.getAccessToken()).isNull()
        assertThat(appDataStore.getRefreshToken()).isNull()
        assertThat(authRepository.getAuthRole()).isEqualTo(AuthRole.Unknown)
        coEvery { authRemoteDataSource.callRegister(any()) }
    }

    @Test
    fun `login correct return success`() = runTest {
        val email = "example@gmail.com"
        val password = "1234"
        val name = "Android"
        val mobileNo = null
        val address = null
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        val tokenResponse = TokenResponse(
            accessToken,
            refreshToken,
        )
        val response = BaseResponse(result = tokenResponse)
        coEvery { authRemoteDataSource.callRegister(any()) } returns response

        useCase(email, password, name, mobileNo, address)

        assertThat(appDataStore.getAccessToken()).isEqualTo(accessToken)
        assertThat(appDataStore.getRefreshToken()).isEqualTo(refreshToken)
        assertThat(authRepository.getAuthRole()).isEqualTo(AuthRole.Auth)
        coEvery { authRemoteDataSource.callRegister(any()) }
    }
}