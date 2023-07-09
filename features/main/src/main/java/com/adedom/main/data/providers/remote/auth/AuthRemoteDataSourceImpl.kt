package com.adedom.main.data.providers.remote.auth

import com.adedom.core.data.providers.datastore.AppDataStore
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.main.BuildConfig
import com.myfood.server.data.models.base.BaseResponse
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders

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