package com.adedom.main.domain.use_cases

import com.adedom.data.repositories.FavoriteRepository
import com.adedom.data.repositories.FoodRepository
import com.adedom.main.domain.repositories.AuthLogoutRepository
import com.adedom.main.domain.repositories.HomeCategoryRepository
import com.adedom.profile.repositories.UserProfileRepository

class LogoutUseCase(
    private val authLogoutRepository: AuthLogoutRepository,
    private val categoryRepository: HomeCategoryRepository,
    private val favoriteRepository: FavoriteRepository,
    private val foodRepository: FoodRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    suspend operator fun invoke() {
        authLogoutRepository.setUnAuthRole()
        categoryRepository.deleteCategoryAll()
        favoriteRepository.deleteFavoriteAll()
        foodRepository.deleteFoodAll()
        userProfileRepository.deleteUserProfile()
        authLogoutRepository.callLogout()
    }
}