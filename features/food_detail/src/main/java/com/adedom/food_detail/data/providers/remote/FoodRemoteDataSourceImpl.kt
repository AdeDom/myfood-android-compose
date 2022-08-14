package com.adedom.food_detail.data.providers.remote

import com.adedom.core.data.providers.remote.DataSourceProvider
import com.adedom.food_detail.BuildConfig
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.request.*

class FoodRemoteDataSourceImpl(
    private val engine: HttpClientEngine,
    private val dataSourceProvider: DataSourceProvider,
) : FoodRemoteDataSource {

    override suspend fun callFoodDetail(foodId: Int): BaseResponse<FoodDetailResponse> {
        return dataSourceProvider.getHttpClient(engine)
            .get(BuildConfig.BASE_URL + "api/food/detail?foodId=$foodId")
            .body()
    }
}