package com.adedom.profile.providers.remote

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.data.profile.BuildConfig
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.UserProfileResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ProfileRemoteDataSourceImpl(
    private val dataProviderRemote: DataProviderRemote,
    private val appDataStore: AppDataStore,
) : ProfileRemoteDataSource {

    override suspend fun callUserProfile(): BaseResponse<UserProfileResponse> {
        return dataProviderRemote.getHttpClient()
            .get(BuildConfig.BASE_URL + "api/profile/user") {
                header(HttpHeaders.Authorization, "Bearer ${appDataStore.getAccessToken()}")
            }
            .body()
    }
}