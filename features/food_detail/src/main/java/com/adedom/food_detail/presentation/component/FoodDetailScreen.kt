package com.adedom.food_detail.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.kodein.di.compose.rememberInstance

@Composable
fun FoodDetailScreen(foodId: Int?) {
    val viewModel by rememberInstance<FoodDetailViewModel>()

    viewModel.setFoodId(foodId)

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Food detail : ${viewModel.uiState.foodId}")
    }
}