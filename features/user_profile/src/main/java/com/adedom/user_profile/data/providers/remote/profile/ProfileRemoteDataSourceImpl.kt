package com.adedom.user_profile.data.providers.remote.profile

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.UserProfileResponse
import com.adedom.user_profile.BuildConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ProfileRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val appDataStore: AppDataStore,
) : ProfileRemoteDataSource {

    override suspend fun callUserProfile(): BaseResponse<UserProfileResponse> {
        return httpClient
            .get(BuildConfig.BASE_URL + "api/profile/user") {
                header(HttpHeaders.Authorization, "Bearer ${appDataStore.getAccessToken()}")
            }
            .body()
    }
}