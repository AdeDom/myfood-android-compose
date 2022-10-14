package com.adedom.data.repositories

import com.myfood.server.data.models.response.FavoriteResponse
import myfood.database.FavoriteEntity

interface FavoriteRepository {

    suspend fun callFavoriteAll(): List<FavoriteResponse>?

    suspend fun getFavoriteList(): List<FavoriteEntity>

    suspend fun saveFavoriteAll(favoriteList: List<FavoriteEntity>)

    suspend fun deleteFavoriteAll()
}