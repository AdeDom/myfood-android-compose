package com.adedom.main.data.providers.remote.auth

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.data.providers.remote.DataSourceProvider
import com.adedom.main.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthRemoteDataSourceImpl(
    private val engine: HttpClientEngine,
    private val appDataStore: AppDataStore,
    private val dataSourceProvider: DataSourceProvider,
) : AuthRemoteDataSource {

    override suspend fun callLogout(): BaseResponse<String> {
        return dataSourceProvider.getHttpClient(engine)
            .post(BuildConfig.BASE_URL + "api/auth/logout") {
                header(HttpHeaders.Authorization, "Bearer ${appDataStore.getAccessToken()}")
            }
            .body()
    }
}