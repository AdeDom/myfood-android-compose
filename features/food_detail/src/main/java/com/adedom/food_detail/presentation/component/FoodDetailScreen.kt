package com.adedom.food_detail.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun FoodDetailScreen(foodId: Int?) {
    val viewModel by rememberInstance<FoodDetailViewModel>()

    LaunchedEffect(key1 = viewModel) {
        viewModel.callFoodDetail(foodId)
    }

    val state = viewModel.uiState

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Text(text = "Food detail : ${state.foodDetail?.foodName}")
        }
    }
}