package com.adedom.data.repositories

import com.adedom.data.providers.local.FavoriteLocalDataSource
import com.adedom.data.providers.remote.FavoriteRemoteDataSource
import com.myfood.server.data.models.response.FavoriteResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import myfood.database.FavoriteEntity

class FavoriteRepositoryImpl(
    private val favoriteLocalDataSource: FavoriteLocalDataSource,
    private val favoriteRemoteDataSource: FavoriteRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : FavoriteRepository {

    override suspend fun callFavoriteAll(): List<FavoriteResponse>? {
        return withContext(ioDispatcher) {
            val favoriteAllResponse = favoriteRemoteDataSource.callFavoriteAll()
            favoriteAllResponse.result
        }
    }

    override suspend fun getFavoriteByFoodId(foodId: Long): FavoriteEntity? {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.getFavoriteByFoodId(foodId)
        }
    }

    override fun getIsFavoriteByFoodIdFlow(foodId: Long): Flow<Long?> {
        return favoriteLocalDataSource.getIsFavoriteByFoodIdFlow(foodId).flowOn(ioDispatcher)
    }

    override suspend fun insertOrReplaceFavorite(favorite: FavoriteEntity) {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.insertOrReplaceFavorite(favorite)
        }
    }

    override suspend fun insertOrReplaceFavoriteAll(favoriteList: List<FavoriteEntity>) {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.insertOrReplaceFavoriteAll(favoriteList)
        }
    }

    override suspend fun updateBackupFavorite(favoriteId: String, updated: String) {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.updateBackupFavorite(favoriteId, updated)
        }
    }

    override suspend fun deleteFavoriteAll() {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.deleteFavoriteAll()
        }
    }
}