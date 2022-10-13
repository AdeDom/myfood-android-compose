package com.adedom.data.providers.local

import com.adedom.myfood.MyFoodDatabase
import com.myfood.server.data.models.web_sockets.FavoriteWebSocketsResponse
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import myfood.database.FoodEntity
import myfood.database.FoodQueries
import java.util.*

class FoodLocalDataSourceImpl(
    db: MyFoodDatabase,
) : FoodLocalDataSource {

    private val queries: FoodQueries = db.foodQueries

    override fun getFoodListByCategoryIdFlow(categoryId: Long): Flow<List<FoodEntity>> {
        return queries.getFoodListByCategoryId(categoryId).asFlow().mapToList()
    }

    override suspend fun getFoodList(): List<FoodEntity> {
        return queries.getFoodList().executeAsList()
    }

    override suspend fun getFoodListByCategoryId(categoryId: Long): List<FoodEntity> {
        return queries.getFoodListByCategoryId(categoryId).executeAsList()
    }

    override suspend fun getFoodListBySearch(search: String): List<FoodEntity> {
        return queries.getFoodListBySearch(search).executeAsList()
    }

    override suspend fun saveFoodAll(foodList: List<FoodEntity>) {
        return foodList.forEach { food ->
            queries.saveFood(
                alias = food.alias,
                categoryId = food.categoryId,
                created = food.created,
                description = food.description,
                favorite = food.favorite,
                foodIdKey = food.foodIdKey,
                foodIdRef = food.foodIdRef,
                foodName = food.foodName,
                search = food.foodName.lowercase(Locale.getDefault()),
                image = food.image,
                price = food.price,
                ratingScore = food.ratingScore,
                ratingScoreCount = food.ratingScoreCount,
                status = food.status,
                updated = food.updated,
            )
        }
    }

    override suspend fun updateFavoriteByFoodId(favorite: FavoriteWebSocketsResponse?) {
        return queries.updateFavoriteByFoodId(favorite?.favorite, favorite?.foodId?.toLong() ?: 0L)
    }

    override suspend fun deleteFoodAll() {
        return queries.deleteFoodAll()
    }
}