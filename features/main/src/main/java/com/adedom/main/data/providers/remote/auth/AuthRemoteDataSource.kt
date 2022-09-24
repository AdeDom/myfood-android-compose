package com.adedom.main.data.providers.remote.auth

import com.myfood.server.data.models.base.BaseResponse

interface AuthRemoteDataSource {

    suspend fun callLogout(): BaseResponse<String>
}