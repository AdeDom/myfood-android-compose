package com.adedom.main.presentation.state

import com.adedom.main.domain.models.UserProfileModel

data class MainUiState(
    val userProfile: UserProfileModel? = null,
)