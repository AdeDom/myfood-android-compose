package com.adedom.core.data.providers.remote

import com.adedom.core.BuildConfig
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.base.ErrorResponse
import com.adedom.myfood.data.models.request.TokenRequest
import com.adedom.myfood.data.models.response.TokenResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class DataProviderRemote(
    private val appHttpClientEngine: AppHttpClientEngine,
    private val appDataStore: AppDataStore,
) {

    fun getHttpClient(): HttpClient {
        return HttpClient(appHttpClientEngine.engine) {
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
                        val baseError = BaseError(
                            code = ErrorResponse.RefreshTokenError.code,
                            message = ErrorResponse.RefreshTokenError.message,
                        )
                        val accessToken = appDataStore.getAccessToken()
                            ?: throw RefreshTokenExpiredException(baseError)
                        val refreshToken = appDataStore.getRefreshToken()
                            ?: throw RefreshTokenExpiredException(baseError)
                        BearerTokens(accessToken, refreshToken)
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