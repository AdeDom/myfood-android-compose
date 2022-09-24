package com.adedom.profile.providers.remote

import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.UserProfileResponse

interface ProfileRemoteDataSource {

    suspend fun callUserProfile(): BaseResponse<UserProfileResponse>
}