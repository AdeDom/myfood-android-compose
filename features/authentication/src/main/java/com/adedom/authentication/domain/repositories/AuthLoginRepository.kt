package com.adedom.authentication.domain.repositories

import com.adedom.core.data.Resource
import com.adedom.core.utils.AuthRole
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.TokenResponse

interface AuthLoginRepository {

    suspend fun callLogin(loginRequest: LoginRequest): Resource<TokenResponse>

    suspend fun saveToken(accessToken: String, refreshToken: String)

    suspend fun saveAuthRole()

    suspend fun getAuthRole(): AuthRole
}