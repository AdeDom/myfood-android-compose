package com.adedom.authentication.domain.repositories

import com.adedom.core.utils.AuthRole
import com.myfood.server.data.models.request.LoginRequest
import com.myfood.server.data.models.response.TokenResponse

interface AuthLoginRepository {

    suspend fun callLogin(loginRequest: LoginRequest): TokenResponse?

    suspend fun saveToken(accessToken: String, refreshToken: String)

    suspend fun saveAuthRole()

    suspend fun getAuthRole(): AuthRole
}