package com.adedom.main.data.providers.local.food

import com.adedom.myfood.MyFoodDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import myfood.database.FoodEntity
import myfood.database.FoodQueries
import java.util.*

class FoodLocalDataSourceImpl(
    db: MyFoodDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FoodLocalDataSource {

    private val queries: FoodQueries = db.foodQueries

    override fun getFoodListFlow(): Flow<List<FoodEntity>> {
        return queries.getFoodList().asFlow().mapToList(ioDispatcher)
    }

    override suspend fun getFoodList(): List<FoodEntity> {
        return queries.getFoodList().executeAsList()
    }

    override suspend fun getFoodListByCategoryId(categoryId: Long): List<FoodEntity> {
        return queries.getFoodListByCategoryId(categoryId).executeAsList()
    }

    override suspend fun saveFoodAll(foodList: List<FoodEntity>) {
        return foodList.forEach { food ->
            queries.saveFood(
                alias = food.alias,
                categoryId = food.categoryId,
                created = food.created,
                description = food.description,
                favorite = food.favorite,
                foodId = food.foodId,
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

    override suspend fun deleteFoodAll() {
        return queries.deleteFoodAll()
    }
}