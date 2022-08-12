package com.adedom.data.repositories.auth

import com.adedom.core.utils.AuthRole
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.TokenResponse

interface AuthLoginRepository {
    suspend fun callLogin(loginRequest: LoginRequest): TokenResponse?
    suspend fun saveToken(accessToken: String, refreshToken: String)
    suspend fun saveAuthRole()
    suspend fun getAuthRole(): AuthRole
}