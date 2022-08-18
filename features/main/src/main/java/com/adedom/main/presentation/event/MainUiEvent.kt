package com.adedom.main.presentation.event

import com.adedom.main.domain.models.MainContentModel
import com.adedom.main.domain.models.UserProfileModel

sealed interface MainUiEvent {

    object Logout : MainUiEvent

    data class FoodDetail(
        val foodId: Int,
    ) : MainUiEvent

    data class SaveState(
        val userProfile: UserProfileModel? = null,
        val mainContent: MainContentModel? = null,
    ) : MainUiEvent
}