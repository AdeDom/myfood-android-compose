package com.adedom.authentication.domain

import com.adedom.authentication.data.providers.data_store.FakeAppDataStore
import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.data.repositories.AuthLoginRepositoryImpl
import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.authentication.domain.use_cases.LoginUseCase
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

class LoginUseCaseTest {

    private val authRemoteDataSource: AuthRemoteDataSource = mockk()
    private lateinit var appDataStore: AppDataStore
    private lateinit var authLoginRepository: AuthLoginRepository
    private lateinit var useCase: LoginUseCase

    @Before
    fun setUp() {
        appDataStore = FakeAppDataStore()
        authLoginRepository = AuthLoginRepositoryImpl(
            appDataStore,
            authRemoteDataSource,
        )
        useCase = LoginUseCase(
            authLoginRepository,
        )
    }

    @Test(expected = ApiServiceException::class)
    fun `login incorrect return error`() = runTest {
        val email = "example@gmail.com"
        val password = "1234"
        val messageError = "Api error."
        val baseError = BaseError(message = messageError)
        val exception = ApiServiceException(baseError)
        coEvery { authRemoteDataSource.callLogin(any()) } throws exception

        useCase(email, password)

        assertThat(appDataStore.getAccessToken()).isNull()
        assertThat(appDataStore.getRefreshToken()).isNull()
        assertThat(authLoginRepository.getAuthRole()).isEqualTo(AuthRole.Unknown)
        coEvery { authRemoteDataSource.callLogin(any()) }
    }

    @Test(expected = Throwable::class)
    fun `login correct token is null return error`() = runTest {
        val email = "example@gmail.com"
        val password = "1234"
        coEvery { authRemoteDataSource.callLogin(any()) } returns BaseResponse()

        useCase(email, password)

        assertThat(appDataStore.getAccessToken()).isNull()
        assertThat(appDataStore.getRefreshToken()).isNull()
        assertThat(authLoginRepository.getAuthRole()).isEqualTo(AuthRole.Unknown)
        coEvery { authRemoteDataSource.callLogin(any()) }
    }

    @Test
    fun `login correct return success`() = runTest {
        val email = "example@gmail.com"
        val password = "1234"
        val accessToken = "accessToken"
        val refreshToken = "refreshToken"
        val tokenResponse = TokenResponse(
            accessToken,
            refreshToken,
        )
        val response = BaseResponse(result = tokenResponse)
        coEvery { authRemoteDataSource.callLogin(any()) } returns response

        useCase(email, password)

        assertThat(appDataStore.getAccessToken()).isEqualTo(accessToken)
        assertThat(appDataStore.getRefreshToken()).isEqualTo(refreshToken)
        assertThat(authLoginRepository.getAuthRole()).isEqualTo(AuthRole.Auth)
        coEvery { authRemoteDataSource.callLogin(any()) }
    }
}