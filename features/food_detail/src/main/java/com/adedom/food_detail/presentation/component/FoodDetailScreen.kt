package com.adedom.food_detail.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adedom.food_detail.presentation.event.FoodDetailUiEvent
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import com.adedom.ui_components.components.AppErrorAlertDialog
import com.adedom.ui_components.theme.MyFoodTheme
import org.kodein.di.compose.rememberInstance

@Composable
fun FoodDetailScreen(
    foodId: Int?,
    onNavigate: (FoodDetailUiEvent) -> Unit,
) {
    val viewModel by rememberInstance<FoodDetailViewModel>()

    LaunchedEffect(key1 = viewModel) {
        viewModel.callFoodDetail(foodId)
    }

    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onNavigate(uiEvent)
        }
    }

    val state = viewModel.uiState

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (state.error != null) {
            AppErrorAlertDialog(
                error = state.error,
                onDismiss = viewModel::setOnBackPressedEvent,
            )
        } else {
            FoodDetailContent()
        }
    }
}

@Composable
fun FoodDetailContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Hello")
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailContentPreview() {
    MyFoodTheme {
        FoodDetailContent()
    }
}