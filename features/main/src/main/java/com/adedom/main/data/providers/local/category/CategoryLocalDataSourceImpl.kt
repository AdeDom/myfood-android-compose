package com.adedom.main.data.providers.local.category

import com.adedom.myfood.MyFoodDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import myfood.database.CategoryEntity
import myfood.database.CategoryQueries

class CategoryLocalDataSourceImpl(
    db: MyFoodDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CategoryLocalDataSource {

    private val queries: CategoryQueries = db.categoryQueries

    override fun getCategoryListFlow(): Flow<List<CategoryEntity>> {
        return queries.getCategoryList().asFlow().mapToList(ioDispatcher)
    }

    override suspend fun getCategoryList(): List<CategoryEntity> {
        return queries.getCategoryList().executeAsList()
    }

    override suspend fun getCategoryNameByCategoryId(categoryId: Long): String {
        return queries.getCategoryNameByCategoryId(categoryId).executeAsOne()
    }

    override suspend fun saveCategoryAll(categories: List<CategoryEntity>) {
        return categories.forEach { category ->
            queries.saveCategory(
                categoryId = category.categoryId,
                categoryName = category.categoryName,
                categoryTypeName = category.categoryTypeName,
                created = category.created,
                image = category.image,
                updated = category.updated,
            )
        }
    }

    override suspend fun deleteCategoryAll() {
        return queries.deleteCategoryAll()
    }
}