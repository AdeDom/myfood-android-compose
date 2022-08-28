package com.adedom.user_profile.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppText
import com.adedom.ui_components.theme.MyFoodTheme
import com.adedom.user_profile.presentation.view_model.UserProfileUiAction
import com.adedom.user_profile.presentation.view_model.UserProfileUiEvent
import com.adedom.user_profile.presentation.view_model.UserProfileUiState
import com.adedom.user_profile.presentation.view_model.UserProfileViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun UserProfileScreen(
    onNavigate: (UserProfileUiEvent) -> Unit,
) {
    val viewModel: UserProfileViewModel = getViewModel()

    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    UserProfileContent(
        viewModel.uiState,
        viewModel::dispatch,
    )
}

@Composable
fun UserProfileContent(
    state: UserProfileUiState,
    dispatch: (UserProfileUiAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        AppIcon(
            image = Icons.Default.ArrowBack,
            modifier = Modifier.clickable { dispatch(UserProfileUiAction.BackPressed) },
        )
        AppText(text = state.userProfile?.email ?: "-")
    }
}

@Composable
@Preview(showBackground = true)
fun UserProfileContentPreview() {
    MyFoodTheme {
        UserProfileContent(
            state = UserProfileUiState(),
            dispatch = {},
        )
    }
}