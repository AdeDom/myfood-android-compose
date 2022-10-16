package com.adedom.authentication.data.repositories

import com.adedom.authentication.data.providers.data_store.FakeAppDataStore
import com.adedom.authentication.data.providers.remote.auth.AuthRemoteDataSource
import com.adedom.authentication.domain.repositories.AuthLoginRepository
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.google.common.truth.Truth.assertThat
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.response.TokenResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class AuthLoginRepositoryImplTest {

    private val authRemoteDataSource: AuthRemoteDataSource = mockk()
    private lateinit var appDataStore: AppDataStore
    private lateinit var repository: AuthLoginRepository

    @Before
    fun setUp() {
        appDataStore = FakeAppDataStore()
        repository = AuthLoginRepositoryImpl(
            appDataStore,
            authRemoteDataSource,
        )
    }

    @Test
    fun `call login correct should be success`() = runTest {
        val loginRequest = LoginRequest(
            email = "email",
            password = "password",
        )
        val jsonString = """
            {
                "version": "1.0",
                "status": "success",
                "result": {
                    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJteS1mb29kIiwidXNlcl9pZCI6IjdlNmU0ZGI2YTA5YzQzZDFhMWUzZWQ4MTU2NzUwZTg4IiwiaXNzIjoiaHR0cHM6Ly9naXRodWIuY29tL0FkZURvbSIsImV4cCI6MTY1OTkwOTIxN30.lNsomHLL1q2wrkVxt2jjrVRpX2vWHU0u966-nMo7aHpp_V5Ym1b05Wz0ffqo18orts667LqA3PCef15IFxIbtQ",
                    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJteS1mb29kIiwidXNlcl9pZCI6IjdlNmU0ZGI2YTA5YzQzZDFhMWUzZWQ4MTU2NzUwZTg4IiwiaXNzIjoiaHR0cHM6Ly9naXRodWIuY29tL0FkZURvbSIsImV4cCI6MTY2MDQyNzYxN30.tME-OIro476Byg-FZGHDYl2iImVlkSIXOul-PfclMhRDA1WPSRT8PQ4DHrQWfDHpxk-pvuywVT3eO6sahdbVPw"
                }
            }
        """.trimIndent()
        val response = Json.decodeFromString<BaseResponse<TokenResponse>>(jsonString)
        coEvery { authRemoteDataSource.callLogin(any()) } returns response

        val result = repository.callLogin(loginRequest)

        assertThat(result).isEqualTo(response.result)
        coVerify { authRemoteDataSource.callLogin(any()) }
    }

    @Test(expected = ApiServiceException::class)
    fun `call login incorrect should be error`() = runTest {
        val loginRequest = LoginRequest(
            email = "email",
            password = "password",
        )
        val jsonString = """
            {
                "version": "1.0",
                "status": "error",
                "error": {
                    "message": "Email or password incorrect."
                }
            }
        """.trimIndent()
        val response = Json.decodeFromString<BaseResponse<TokenResponse>>(jsonString)
        val exception = ApiServiceException(response.error)
        coEvery { authRemoteDataSource.callLogin(any()) } throws exception

        val result = repository.callLogin(loginRequest)

        assertThat(result).isEqualTo(response.error)
        coVerify { authRemoteDataSource.callLogin(any()) }
    }

    @Test
    fun `save access token and refresh token should return token`() = runTest {
        val accessToken = "123"
        val refreshToken = "456"

        repository.saveToken(accessToken, refreshToken)

        assertThat(appDataStore.getAccessToken()).isEqualTo(accessToken)
        assertThat(appDataStore.getRefreshToken()).isEqualTo(refreshToken)
    }

    @Test
    fun `save auth role should return auth role`() = runTest {
        repository.saveAuthRole()

        assertThat(appDataStore.getAuthRole()).isEqualTo(AuthRole.Auth)
    }
}