package com.adedom.search_food.domain.repositories

import myfood.database.FoodEntity

interface SearchFoodRepository {

    suspend fun getFoodListBySearch(search: String): List<FoodEntity>
}