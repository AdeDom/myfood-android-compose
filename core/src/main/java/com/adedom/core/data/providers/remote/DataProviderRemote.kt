package com.adedom.core.data.providers.remote

import com.adedom.core.BuildConfig
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.adedom.core.utils.RefreshTokenExpiredException
import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.base.ErrorResponse
import com.myfood.server.data.models.request.TokenRequest
import com.myfood.server.data.models.response.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class DataProviderRemote(
    private val appHttpClientEngine: AppHttpClientEngine,
    private val appDataStore: AppDataStore,
) {

    fun getHttpClient(): HttpClient {
        return HttpClient(appHttpClientEngine.engine) {
            install(WebSockets)

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 60_000
            }

            if (BuildConfig.IS_DEVELOP_MODE) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.HEADERS
                }
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = appDataStore.getAccessToken()
                        val refreshToken = appDataStore.getRefreshToken()
                        if (accessToken != null && refreshToken != null) {
                            BearerTokens(accessToken, refreshToken)
                        } else {
                            null
                        }
                    }

                    refreshTokens {
                        val tokenRequest = TokenRequest(
                            accessToken = oldTokens?.accessToken,
                            refreshToken = oldTokens?.refreshToken,
                        )
                        val tokenResponse: BaseResponse<TokenResponse> = client
                            .post("${BuildConfig.BASE_URL}api/auth/refreshToken") {
                                contentType(ContentType.Application.Json)
                                setBody(tokenRequest)
                            }
                            .body()
                        val baseError = BaseError(
                            code = ErrorResponse.RefreshTokenError.code,
                            message = ErrorResponse.RefreshTokenError.message,
                        )
                        val accessToken = tokenResponse.result?.accessToken
                            ?: throw RefreshTokenExpiredException(baseError)
                        val refreshToken = tokenResponse.result?.refreshToken
                            ?: throw RefreshTokenExpiredException(baseError)
                        appDataStore.setAccessToken(accessToken)
                        appDataStore.setRefreshToken(refreshToken)
                        BearerTokens(accessToken, refreshToken)
                    }

                    sendWithoutRequest { request ->
                        request.url.host == BuildConfig.HOST
                    }
                }
            }

            expectSuccess = true
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    when (exception) {
                        is ClientRequestException -> {
                            val exceptionResponse = exception.response
                            when (exceptionResponse.status) {
                                HttpStatusCode.BadRequest -> {
                                    val jsonString = exceptionResponse.bodyAsText()
                                    val baseResponse =
                                        jsonString.decodeApiServiceResponseFromString()
                                    val baseError = baseResponse.error
                                    throw ApiServiceException(baseError)
                                }
                                HttpStatusCode.Forbidden -> {
                                    appDataStore.setAccessToken("")
                                    appDataStore.setRefreshToken("")
                                    appDataStore.setAuthRole(AuthRole.UnAuth)
                                    val jsonString = exceptionResponse.bodyAsText()
                                    val baseResponse =
                                        jsonString.decodeApiServiceResponseFromString()
                                    val baseError = baseResponse.error
                                    throw RefreshTokenExpiredException(baseError)
                                }
                            }
                        }
                        else -> {
                            val messageString = exception.message
                            val baseError = BaseError(message = messageString)
                            throw ApiServiceException(baseError)
                        }
                    }
                }
            }
        }
    }
}