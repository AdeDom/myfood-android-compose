package com.adedom.main.data.providers.remote.auth

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.TokenResponse

interface AuthRemoteDataSource {

    suspend fun callLogin(loginRequest: LoginRequest): BaseResponse<TokenResponse>

    suspend fun callLogout(): BaseResponse<String>
}