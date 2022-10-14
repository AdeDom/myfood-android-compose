package com.adedom.data.repositories

import com.adedom.data.providers.remote.FavoriteRemoteDataSource
import com.myfood.server.data.models.response.FavoriteResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepositoryImpl(
    private val favoriteRemoteDataSource: FavoriteRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FavoriteRepository {

    override suspend fun callFavoriteAll(): List<FavoriteResponse>? {
        return withContext(ioDispatcher) {
            val favoriteAllResponse = favoriteRemoteDataSource.callFavoriteAll()
            favoriteAllResponse.result
        }
    }
}