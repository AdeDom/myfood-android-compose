package com.adedom.data.repositories

import com.myfood.server.data.models.response.FavoriteResponse

interface FavoriteRepository {

    suspend fun callFavoriteAll(): List<FavoriteResponse>?
}