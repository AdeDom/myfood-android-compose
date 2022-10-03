package com.adedom.data.repositories

import com.adedom.data.models.MyFavoriteResponse
import com.adedom.data.providers.web_sockets.FavoriteWebSocketDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FavoriteWebSocketRepositoryImpl(
    private val favoriteWebSocketDataSource: FavoriteWebSocketDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FavoriteWebSocketRepository {

    override suspend fun init(): Unit? {
        return withContext(ioDispatcher) {
            favoriteWebSocketDataSource.init()
        }
    }

    override fun isActive(): Boolean {
        return favoriteWebSocketDataSource.isActive()
    }

    override fun observe(): Flow<MyFavoriteResponse> {
        return favoriteWebSocketDataSource.observe().flowOn(ioDispatcher)
    }

    override suspend fun send(foodId: Int): Unit? {
        return withContext(ioDispatcher) {
            favoriteWebSocketDataSource.send(foodId)
        }
    }

    override suspend fun close(): Unit? {
        return withContext(ioDispatcher) {
            favoriteWebSocketDataSource.close()
        }
    }
}