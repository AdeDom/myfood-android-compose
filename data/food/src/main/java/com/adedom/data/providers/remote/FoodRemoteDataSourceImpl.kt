package com.adedom.data.providers.remote

import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.data.food.BuildConfig
import com.myfood.server.data.models.base.BaseResponse
import com.myfood.server.data.models.response.FoodDetailResponse
import io.ktor.client.call.*
import io.ktor.client.request.*

class FoodRemoteDataSourceImpl(
    private val dataProviderRemote: DataProviderRemote,
) : FoodRemoteDataSource {

    override suspend fun callFoodListByCategoryId(categoryId: Int): BaseResponse<List<FoodDetailResponse>> {
        return dataProviderRemote.getHttpClient()
            .get(BuildConfig.BASE_URL + "api/food/getFoodByCategoryId?categoryId=$categoryId")
            .body()
    }

    override suspend fun callFoodDetail(foodId: Int): BaseResponse<FoodDetailResponse> {
        return dataProviderRemote.getHttpClient()
            .get(BuildConfig.BASE_URL + "api/food/detail?foodId=$foodId")
            .body()
    }
}