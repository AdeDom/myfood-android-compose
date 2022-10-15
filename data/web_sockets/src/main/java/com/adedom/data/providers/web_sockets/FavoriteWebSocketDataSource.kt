package com.adedom.data.providers.web_sockets

import com.adedom.data.models.MyFavoriteResponse
import kotlinx.coroutines.flow.Flow

interface FavoriteWebSocketDataSource {

    suspend fun init(): Unit?

    fun isActive(): Boolean

    fun observe(): Flow<MyFavoriteResponse>

    suspend fun send(foodId: Int)

    suspend fun close(): Unit?
}