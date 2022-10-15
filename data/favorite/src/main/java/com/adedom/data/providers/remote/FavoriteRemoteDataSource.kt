package com.adedom.data.providers.remote

import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.FavoriteResponse

interface FavoriteRemoteDataSource {

    suspend fun callFavoriteAll(): BaseResponse<List<FavoriteResponse>>
}