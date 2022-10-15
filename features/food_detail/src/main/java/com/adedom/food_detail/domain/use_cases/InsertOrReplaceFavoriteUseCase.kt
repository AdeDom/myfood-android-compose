package com.adedom.food_detail.domain.use_cases

import com.adedom.data.repositories.FavoriteRepository
import com.adedom.user_profile.domain.use_cases.GetMyUserIdUseCase
import com.myfood.server.utility.constant.AppConstant
import myfood.database.FavoriteEntity
import java.text.SimpleDateFormat
import java.util.*

class InsertOrReplaceFavoriteUseCase(
    private val getMyUserIdUseCase: GetMyUserIdUseCase,
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(foodId: Int?): String {
        if (foodId == null) {
            throw Throwable("Food id is null.")
        }

        val favoriteEntity = favoriteRepository.getFavoriteByFoodId(foodId.toLong())

        val favoriteId = favoriteEntity?.favoriteId ?: UUID.randomUUID().toString().replace("-", "")

        val userId = getMyUserIdUseCase().orEmpty()

        val isFavorite = if (favoriteEntity == null) {
            1L
        } else {
            if (favoriteEntity.isFavorite == 0L) 1L else 0L
        }

        val isBackup = 0L

        val sdf = SimpleDateFormat(AppConstant.DATE_TIME_FORMAT_REQUEST, Locale.getDefault())
        val created = sdf.format(Date())

        val updated = favoriteEntity?.updated

        val favorite = FavoriteEntity(
            favoriteId = favoriteId,
            userId = userId,
            foodId = foodId.toLong(),
            isFavorite = isFavorite,
            isBackup = isBackup,
            created = created,
            updated = updated,
        )
        favoriteRepository.insertOrReplaceFavorite(favorite)

        return favoriteId
    }
}