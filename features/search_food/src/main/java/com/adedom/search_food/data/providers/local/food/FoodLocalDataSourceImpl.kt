package com.adedom.search_food.data.providers.local.food

import com.adedom.myfood.MyFoodDatabase
import myfood.database.FoodEntity
import myfood.database.FoodQueries

class FoodLocalDataSourceImpl(
    db: MyFoodDatabase,
) : FoodLocalDataSource {

    private val queries: FoodQueries = db.foodQueries

    override suspend fun getFoodListBySearch(search: String): List<FoodEntity> {
        return queries.getFoodListBySearch(search).executeAsList()
    }
}