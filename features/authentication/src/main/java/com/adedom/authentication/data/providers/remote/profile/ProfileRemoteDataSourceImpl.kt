package com.adedom.authentication.data.providers.remote.profile

import com.adedom.authentication.BuildConfig
import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.data.providers.remote.DataSourceProvider
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.UserProfileResponse
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.http.*

class ProfileRemoteDataSourceImpl(
    private val engine: HttpClientEngine,
    private val appDataStore: AppDataStore,
    private val dataSourceProvider: DataSourceProvider,
) : ProfileRemoteDataSource {

    override suspend fun callUserProfile(): BaseResponse<UserProfileResponse> {
        return dataSourceProvider.getHttpClient(engine)
            .get(BuildConfig.BASE_URL + "api/profile/user") {
                header(HttpHeaders.Authorization, "Bearer ${appDataStore.getAccessToken()}")
            }
            .body()
    }
}