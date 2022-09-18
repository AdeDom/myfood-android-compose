package com.adedom.food_detail.data.repositories

import com.adedom.core.data.Resource
import com.adedom.core.data.models.error.AppErrorCode
import com.adedom.core.utils.ApiServiceException
import com.adedom.food_detail.data.providers.remote.FoodRemoteDataSource
import com.adedom.food_detail.domain.repositories.FoodDetailRepository
import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.response.FoodDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodDetailRepositoryImpl(
    private val foodRemoteDataSource: FoodRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FoodDetailRepository {

    override suspend fun callFoodDetail(foodId: Int): Resource<FoodDetailResponse> {
        return withContext(ioDispatcher) {
            try {
                val foodDetailResponse = foodRemoteDataSource.callFoodDetail(foodId)
                val result = foodDetailResponse.result
                if (result != null) {
                    Resource.Success(result)
                } else {
                    val baseError = BaseError(code = AppErrorCode.DataIsNull.code)
                    Resource.Error(baseError)
                }
            } catch (exception: ApiServiceException) {
                val error = exception.toBaseError()
                Resource.Error(error)
            }
        }
    }
}