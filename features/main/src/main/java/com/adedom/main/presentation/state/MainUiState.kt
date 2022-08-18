package com.adedom.main.presentation.state

import com.adedom.main.domain.models.MainContentModel
import com.adedom.main.domain.models.UserProfileModel
import com.adedom.myfood.data.models.base.BaseError

data class MainUiState(
    val isLoading: Boolean = false,
    val error: BaseError? = null,
    val userProfile: UserProfileModel? = null,
    val mainContent: MainContentModel? = null,
)