package com.adedom.food_detail.data.providers.remote

import com.adedom.food_detail.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class FoodRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : FoodRemoteDataSource {

    override suspend fun callFoodDetail(foodId: Int): BaseResponse<FoodDetailResponse> {
        return httpClient
            .get(BuildConfig.BASE_URL + "api/food/detail?foodId=$foodId")
            .body()
    }
}