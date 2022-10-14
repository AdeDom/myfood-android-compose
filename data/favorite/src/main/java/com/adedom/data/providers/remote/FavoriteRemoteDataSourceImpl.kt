package com.adedom.data.providers.remote

import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.data.favorite.BuildConfig
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.FavoriteResponse
import io.ktor.client.call.*
import io.ktor.client.request.*

class FavoriteRemoteDataSourceImpl(
    private val dataProviderRemote: DataProviderRemote,
) : FavoriteRemoteDataSource {

    override suspend fun callFavoriteAll(): BaseResponse<List<FavoriteResponse>> {
        return dataProviderRemote.getHttpClient()
            .get(BuildConfig.BASE_URL + "api/favorite/getFavoriteAll")
            .body()
    }
}