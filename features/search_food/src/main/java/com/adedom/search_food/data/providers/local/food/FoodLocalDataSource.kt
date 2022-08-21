package com.adedom.search_food.data.providers.local.food

import myfood.database.FoodEntity

interface FoodLocalDataSource {

    suspend fun getFoodListBySearch(search: String): List<FoodEntity>
}