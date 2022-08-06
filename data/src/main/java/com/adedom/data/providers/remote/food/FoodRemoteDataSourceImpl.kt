package com.adedom.data.providers.remote.food

import com.adedom.data.BuildConfig
import com.adedom.data.providers.remote.DataSourceProvider
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.request.*

class FoodRemoteDataSourceImpl(
    private val engine: HttpClientEngine,
    private val dataSourceProvider: DataSourceProvider,
) : FoodRemoteDataSource {

    override suspend fun callFoodListByCategoryId(categoryId: Int): BaseResponse<List<FoodDetailResponse>> {
        return dataSourceProvider.getHttpClient(engine)
            .get(BuildConfig.BASE_URL + "api/food/getFoodByCategoryId?categoryId=$categoryId")
            .body()
    }
}