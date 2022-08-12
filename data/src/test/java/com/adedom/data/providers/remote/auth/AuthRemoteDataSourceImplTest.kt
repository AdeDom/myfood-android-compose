package com.adedom.data.providers.remote.auth

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.data.providers.remote.DataSourceProvider
import com.adedom.core.utils.ApiServiceException
import com.adedom.data.providers.data_store.FakeAppDataStore
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.TokenResponse
import com.google.common.truth.Truth.assertThat
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class AuthRemoteDataSourceImplTest {

    private lateinit var appDataStore: AppDataStore
    private lateinit var dataSourceProvider: DataSourceProvider
    private lateinit var dataSource: AuthRemoteDataSource

    @Before
    fun setUp() {
        appDataStore = FakeAppDataStore()
        dataSourceProvider = DataSourceProvider(appDataStore)
    }

    @Test
    fun `call login should correct return success`() = runTest {
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
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(jsonString),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        dataSource = AuthRemoteDataSourceImpl(
            mockEngine,
            appDataStore,
            dataSourceProvider,
        )

        val result = dataSource.callLogin(loginRequest)

        val response = Json.decodeFromString<BaseResponse<TokenResponse>>(jsonString)
        assertThat(result).isEqualTo(response)
    }

    @Test(expected = ApiServiceException::class)
    fun `call login should incorrect return error`() = runTest {
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
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(jsonString),
                status = HttpStatusCode.BadRequest,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        dataSource = AuthRemoteDataSourceImpl(
            mockEngine,
            appDataStore,
            dataSourceProvider,
        )

        dataSource.callLogin(loginRequest)
    }
}