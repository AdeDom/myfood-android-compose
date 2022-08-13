package com.adedom.authentication.presentation.state

sealed interface RegisterUiState {
    object Initial : RegisterUiState
    object Login : RegisterUiState
}