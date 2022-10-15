package com.adedom.food_detail.domain.use_cases

import com.adedom.data.repositories.FavoriteRepository
import com.myfood.server.utility.constant.AppConstant
import java.text.SimpleDateFormat
import java.util.*

class UpdateBackupFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(favoriteId: String) {
        val sdf = SimpleDateFormat(AppConstant.DATE_TIME_FORMAT_REQUEST, Locale.getDefault())
        val updated = sdf.format(Date())
        favoriteRepository.updateBackupFavorite(favoriteId, updated)
    }
}