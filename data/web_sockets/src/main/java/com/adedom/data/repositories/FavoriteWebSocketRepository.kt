package com.adedom.data.repositories

import com.adedom.data.models.MyFavoriteResponse
import kotlinx.coroutines.flow.Flow

interface FavoriteWebSocketRepository {

    suspend fun init(): Unit?

    fun isActive(): Boolean

    fun observe(): Flow<MyFavoriteResponse>

    suspend fun send(foodId: Int): Unit?

    suspend fun close(): Unit?
}