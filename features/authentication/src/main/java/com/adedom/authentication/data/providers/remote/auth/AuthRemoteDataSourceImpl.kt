package com.adedom.authentication.data.providers.remote.auth

import com.adedom.authentication.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.TokenResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : AuthRemoteDataSource {

    override suspend fun callLogin(loginRequest: LoginRequest): BaseResponse<TokenResponse> {
        return httpClient
            .post(BuildConfig.BASE_URL + "api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }
            .body()
    }
}