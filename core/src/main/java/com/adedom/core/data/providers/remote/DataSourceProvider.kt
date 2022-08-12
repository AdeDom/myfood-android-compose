package com.adedom.core.data.providers.remote

import com.adedom.core.BuildConfig
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.utils.ApiServiceException
import com.adedom.core.utils.AuthRole
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.TokenRequest
import com.adedom.myfood.data.models.response.TokenResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
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

class DataSourceProvider(
    private val appDataStore: AppDataStore,
) {

    fun getHttpClient(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
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
                        val accessToken = appDataStore.getAccessToken().orEmpty()
                        val refreshToken = appDataStore.getRefreshToken().orEmpty()
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
                        val accessToken = tokenResponse.result?.accessToken.orEmpty()
                        val refreshToken = tokenResponse.result?.refreshToken.orEmpty()
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
            httpResponseValidator()
        }
    }

    private fun HttpClientConfig<*>.httpResponseValidator() {
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                val clientException = exception as? ClientRequestException
                    ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = clientException.response
                when (exceptionResponse.status) {
                    HttpStatusCode.BadRequest -> {
                        val jsonString = exceptionResponse.bodyAsText()
                        val baseResponse = jsonString.decodeApiServiceResponseFromString()
                        val baseError = baseResponse.error
                        throw ApiServiceException(baseError)
                    }
                    HttpStatusCode.Forbidden -> {
                        appDataStore.setAccessToken("")
                        appDataStore.setRefreshToken("")
                        appDataStore.setAuthRole(AuthRole.UnAuth)
                        val jsonString = exceptionResponse.bodyAsText()
                        val baseResponse = jsonString.decodeApiServiceResponseFromString()
                        val baseError = baseResponse.error
                        throw RefreshTokenExpiredException(baseError)
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