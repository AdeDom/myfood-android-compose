package com.adedom.data.repositories

import com.adedom.data.providers.local.FavoriteLocalDataSource
import com.adedom.data.providers.remote.FavoriteRemoteDataSource
import com.myfood.server.data.models.response.FavoriteResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import myfood.database.FavoriteEntity

class FavoriteRepositoryImpl(
    private val favoriteLocalDataSource: FavoriteLocalDataSource,
    private val favoriteRemoteDataSource: FavoriteRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FavoriteRepository {

    override suspend fun callFavoriteAll(): List<FavoriteResponse>? {
        return withContext(ioDispatcher) {
            val favoriteAllResponse = favoriteRemoteDataSource.callFavoriteAll()
            favoriteAllResponse.result
        }
    }

    override suspend fun getFavoriteList(): List<FavoriteEntity> {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.getFavoriteList()
        }
    }

    override suspend fun getFavoriteCountByFoodId(foodId: Long): Long? {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.getFavoriteCountByFoodId(foodId)
        }
    }

    override fun getFavoriteCountByFoodIdFlow(foodId: Long): Flow<Long?> {
        return favoriteLocalDataSource.getFavoriteCountByFoodIdFlow(foodId).flowOn(ioDispatcher)
    }

    override suspend fun getIsFavoriteByFoodId(foodId: Long): Long? {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.getIsFavoriteByFoodId(foodId)
        }
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.insertFavorite(favorite)
        }
    }

    override suspend fun saveFavoriteAll(favoriteList: List<FavoriteEntity>) {
        return withContext(ioDispatcher) {
            favoriteLocalDataSource.saveFavoriteAll(favoriteList)
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