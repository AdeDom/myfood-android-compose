package com.adedom.search_food.data.repositories

import com.adedom.search_food.data.providers.local.food.FoodLocalDataSource
import com.adedom.search_food.domain.repositories.SearchFoodRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myfood.database.FoodEntity

class SearchFoodRepositoryImpl(
    private val foodLocalDataSource: FoodLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SearchFoodRepository {

    override suspend fun getFoodListBySearch(search: String): List<FoodEntity> {
        return withContext(ioDispatcher) {
            foodLocalDataSource.getFoodListBySearch(search)
        }
    }
}