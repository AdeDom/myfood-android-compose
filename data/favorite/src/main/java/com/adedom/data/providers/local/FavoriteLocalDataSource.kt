package com.adedom.data.providers.local

import myfood.database.FavoriteEntity

interface FavoriteLocalDataSource {

    suspend fun getFavoriteList(): List<FavoriteEntity>

    suspend fun saveFavoriteAll(favoriteList: List<FavoriteEntity>)

    suspend fun deleteFavoriteAll()
}