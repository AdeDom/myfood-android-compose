package com.adedom.profile.providers.remote

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.data.providers.data_store.FakeAppDataStore
import com.adedom.core.data.providers.remote.AppHttpClientEngine
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.core.utils.ApiServiceException
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.google.common.truth.Truth.assertThat
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class ProfileRemoteDataSourceImplTest {

    private lateinit var appDataStore: AppDataStore

    @Before
    fun setUp() {
        appDataStore = FakeAppDataStore()
    }

    @Test
    fun `call user profile return success`() = runTest {
        val jsonString = """
            {
                "version": "1.0",
                "status": "success",
                "result": {
                    "userId": "7e6e4db6a09c43d1a1e3ed8156750e88",
                    "email": "dom6",
                    "name": "Change profile",
                    "mobileNo": "1234567890",
                    "address": "Phayao",
                    "image": "https://picsum.photos/300/300",
                    "status": "active",
                    "created": "26/3/2022 18:23",
                    "updated": "10/5/2022 19:5"
                }
            }
        """.trimIndent()
        val appHttpClientEngine = object : AppHttpClientEngine {
            override val engine: HttpClientEngine = MockEngine {
                respond(
                    content = ByteReadChannel(jsonString),
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString(),
                    )
                )
            }
        }
        val dataProviderRemote = DataProviderRemote(appHttpClientEngine, appDataStore)
        val dataSource = ProfileRemoteDataSourceImpl(dataProviderRemote, appDataStore)

        val result = dataSource.callUserProfile()

        val response = Json.decodeFromString<BaseResponse<UserProfileResponse>>(jsonString)
        assertThat(result).isEqualTo(response)
    }

    @Test(expected = ApiServiceException::class)
    fun `call user profile return error`() = runTest {
        val jsonString = """
            {
                "version": "1.0",
                "status": "success",
                "error": {
                    "message": "User profile is error"
                }
            }
        """.trimIndent()
        val appHttpClientEngine = object : AppHttpClientEngine {
            override val engine: HttpClientEngine = MockEngine {
                respond(
                    content = ByteReadChannel(jsonString),
                    status = HttpStatusCode.BadRequest,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString(),
                    )
                )
            }
        }
        val dataProviderRemote = DataProviderRemote(appHttpClientEngine, appDataStore)
        val dataSource = ProfileRemoteDataSourceImpl(dataProviderRemote, appDataStore)

        dataSource.callUserProfile()
    }
}