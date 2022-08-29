package com.adedom.main.data.providers.remote.auth

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.main.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthRemoteDataSourceImpl(
    private val dataProviderRemote: DataProviderRemote,
    private val appDataStore: AppDataStore,
) : AuthRemoteDataSource {

    override suspend fun callLogout(): BaseResponse<String> {
        return dataProviderRemote.getHttpClient()
            .post(BuildConfig.BASE_URL + "api/auth/logout") {
                header(HttpHeaders.Authorization, "Bearer ${appDataStore.getAccessToken()}")
            }
            .body()
    }
}