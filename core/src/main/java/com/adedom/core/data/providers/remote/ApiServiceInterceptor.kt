package com.adedom.core.data.providers.remote
//
//import com.adedom.data.BuildConfig
//import com.adedom.data.providers.data_store.AppDataStore
//import com.adedom.data.utils.ApiServiceException
//import com.adedom.data.utils.AuthRole
//import com.adedom.myfood.data.models.base.BaseError
//import com.adedom.myfood.data.models.base.BaseResponse
//import com.adedom.myfood.data.models.request.TokenRequest
//import com.adedom.myfood.data.models.response.TokenResponse
//import io.ktor.client.*
//import io.ktor.client.call.*
//import io.ktor.client.engine.okhttp.*
//import io.ktor.client.plugins.*
//import io.ktor.client.plugins.contentnegotiation.*
//import io.ktor.client.plugins.logging.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import io.ktor.serialization.kotlinx.json.*
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.runBlocking
//import kotlinx.serialization.json.Json
//import okhttp3.Interceptor
//import okhttp3.Response
//import org.json.JSONObject
//
//class ApiServiceInterceptor(
//    private val appDataStore: AppDataStore,
//) : Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val response = chain.proceed(request)
//
//        if (response.isSuccessful) {
//            return response
//        } else {
//            when (response.code) {
//                HttpStatusCode.BadRequest.value -> {
//                    val jsonString = JSONObject(response.body?.string().orEmpty()).toString()
//                    val baseResponse = jsonString.decodeApiServiceResponseFromString()
//                    val baseError = baseResponse.error ?: createBaseError()
//                    throw ApiServiceException(baseError)
//                }
//                HttpStatusCode.Unauthorized.value -> return runBlocking(Dispatchers.IO) {
//                    val jsonString = JSONObject(response.body?.string().orEmpty()).toString()
//                    callRefreshToken(chain, jsonString)
//                }
//                HttpStatusCode.Forbidden.value -> runBlocking(Dispatchers.IO) {
//                    appDataStore.setAccessToken("")
//                    appDataStore.setRefreshToken("")
//                    appDataStore.setAuthRole(AuthRole.UnAuth)
//                    val jsonString = JSONObject(response.body?.string().orEmpty()).toString()
//                    val baseResponse = jsonString.decodeApiServiceResponseFromString()
//                    val baseError = baseResponse.error ?: createBaseError()
//                    throw ApiServiceException(baseError)
//                }
//                in 500..599 -> {
//                    val baseError = createBaseError(response.message)
//                    throw ApiServiceException(baseError)
//                }
//                else -> {
//                    val baseError = createBaseError(response.message)
//                    throw ApiServiceException(baseError)
//                }
//            }
//        }
//    }
//
//    private fun createBaseError(message: String? = null): BaseError {
//        val messageString = message ?: "Error."
//        return BaseError(message = messageString)
//    }
//
//    private suspend fun callRefreshToken(chain: Interceptor.Chain, jsonString: String): Response {
//        val hasRefreshToken = appDataStore.getRefreshToken().isNullOrBlank()
//        if (hasRefreshToken) {
//            appDataStore.setAccessToken("")
//            appDataStore.setRefreshToken("")
//            appDataStore.setAuthRole(AuthRole.UnAuth)
//            val baseResponse = jsonString.decodeApiServiceResponseFromString()
//            val baseError = baseResponse.error ?: createBaseError()
//            throw ApiServiceException(baseError)
//        } else {
//            return try {
//                val tokenRequest = TokenRequest(
//                    accessToken = appDataStore.getAccessToken(),
//                    refreshToken = appDataStore.getRefreshToken(),
//                )
//                val tokenResponse: BaseResponse<TokenResponse> = getHttpClient()
//                    .post("${BuildConfig.BASE_URL}api/auth/refreshToken") {
//                        contentType(ContentType.Application.Json)
//                        setBody(tokenRequest)
//                    }
//                    .body()
//
//                val accessToken = tokenResponse.result?.accessToken.orEmpty()
//                val refreshToken = tokenResponse.result?.refreshToken.orEmpty()
//                appDataStore.setAccessToken(accessToken)
//                appDataStore.setRefreshToken(refreshToken)
//                val request = chain.request()
//                    .newBuilder()
//                    .removeHeader(HttpHeaders.Authorization)
//                    .addHeader(HttpHeaders.Authorization, "Bearer $accessToken")
//                    .build()
//                chain.proceed(request)
//            } catch (exception: ClientRequestException) {
//                // Refresh token expired.
//                appDataStore.setAccessToken("")
//                appDataStore.setRefreshToken("")
//                appDataStore.setAuthRole(AuthRole.UnAuth)
//                val baseError = createBaseError(exception.message)
//                throw ApiServiceException(baseError)
//            }
//        }
//    }
//
//    private fun getHttpClient(): HttpClient {
//        return HttpClient(OkHttp) {
//            install(ContentNegotiation) {
//                json(Json {
//                    ignoreUnknownKeys = true
//                })
//            }
//
//            install(HttpTimeout) {
//                requestTimeoutMillis = 60_000
//            }
//
//            if (BuildConfig.APP_TYPE == "develop") {
//                install(Logging) {
//                    logger = Logger.DEFAULT
//                    level = LogLevel.HEADERS
//                }
//            }
//        }
//    }
//}