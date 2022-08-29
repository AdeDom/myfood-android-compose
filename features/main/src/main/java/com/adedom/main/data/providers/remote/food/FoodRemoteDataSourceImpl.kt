package com.adedom.main.data.providers.remote.food

import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.main.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
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
}