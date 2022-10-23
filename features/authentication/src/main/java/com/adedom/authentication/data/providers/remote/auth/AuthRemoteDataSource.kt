package com.adedom.authentication.data.providers.remote.auth

import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.request.RegisterRequest
import com.myfood.server.data.models.response.TokenResponse

interface AuthRemoteDataSource {

    suspend fun callLogin(loginRequest: LoginRequest): BaseResponse<TokenResponse>

    suspend fun callRegister(registerRequest: RegisterRequest): BaseResponse<TokenResponse>
}