package com.adedom.authentication.data.providers.remote.auth

import com.adedom.authentication.BuildConfig
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.response.TokenResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthRemoteDataSourceImpl(
    private val dataProviderRemote: DataProviderRemote,
) : AuthRemoteDataSource {

    override suspend fun callLogin(loginRequest: LoginRequest): BaseResponse<TokenResponse> {
        return dataProviderRemote.getHttpClient()
            .post(BuildConfig.BASE_URL + "api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }
            .body()
    }
}