package com.adedom.authentication.data.providers.remote.profile

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.UserProfileResponse

interface ProfileRemoteDataSource {

    suspend fun callUserProfile(): BaseResponse<UserProfileResponse>
}