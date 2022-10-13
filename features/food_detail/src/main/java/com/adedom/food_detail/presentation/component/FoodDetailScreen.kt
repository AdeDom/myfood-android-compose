package com.adedom.food_detail.presentation.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.food_detail.R
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.presentation.view_model.FoodDetailUiEvent
import com.adedom.food_detail.presentation.view_model.FoodDetailUiState
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import com.adedom.ui_components.components.*
import com.adedom.ui_components.theme.MyFoodTheme

@Composable
fun FoodDetailScreen(
    viewModel: FoodDetailViewModel,
    foodId: Int?,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.callFoodDetail(foodId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.message.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    FoodDetailContent(
        state = viewModel.uiState,
        viewModel::dispatch,
        onBackPressed,
        foodId,
    )
}

@Composable
fun FoodDetailContent(
    state: FoodDetailUiState,
    dispatch: (FoodDetailUiEvent) -> Unit,
    onBackPressed: () -> Unit,
    foodId: Int?,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (state) {
            FoodDetailUiState.Loading -> {
                AppLoadingLottieAnimation()
            }
            is FoodDetailUiState.Error -> {
                AppErrorAlertDialog(
                    error = state.error,
                    onDismiss = onBackPressed,
                )
            }
            is FoodDetailUiState.Success -> {
                AppImageNetwork(
                    image = state.data.image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeightDp / 2),
                )
                LazyColumn {
                    item {
                        Spacer(
                            modifier = Modifier.height((screenHeightDp / 2) - 128.dp),
                        )
                        Box {
                            Column {
                                Spacer(
                                    modifier = Modifier.height(64.dp),
                                )
                                Card(
                                    shape = RoundedCornerShape(
                                        topStart = 64.dp,
                                        topEnd = 64.dp,
                                    ),
                                    elevation = 16.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(screenHeightDp - 64.dp),
                                ) {
                                    Column(
                                        modifier = Modifier.padding(
                                            start = 32.dp,
                                            end = 32.dp,
                                            top = 32.dp,
                                        )
                                    ) {
                                        Text(
                                            text = state.data.foodName,
                                            color = Color.Black,
                                            fontSize = 24.sp,
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            verticalAlignment = Alignment.Bottom,
                                        ) {
                                            AppIcon(
                                                image = Icons.Default.Star,
                                                color = Color(0xFFFFC107),
                                                modifier = Modifier.size(18.dp),
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = state.data.ratingScoreCount,
                                                color = Color(0xFFFFC107),
                                                fontSize = 14.sp,
                                            )
                                            Box(modifier = Modifier.weight(1f))
                                            Text(
                                                text = "${state.data.price}",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "Bath",
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(32.dp))
                                        Text(
                                            text = "Description",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = state.data.description,
                                            fontSize = 16.sp,
                                        )
                                    }
                                }
                            }
                            if (state.isFavorite) {
                                AppImage(
                                    image = R.drawable.favorite_active,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .offset(
                                            x = screenWidthDp - 128.dp,
                                            y = 16.dp,
                                        )
                                        .clip(CircleShape)
                                        .clickable { dispatch(FoodDetailUiEvent.MyFavorite(foodId)) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailContentPreview() {
    MyFoodTheme {
        FoodDetailContent(
//            state = FoodDetailUiState.Loading,
//            state = FoodDetailUiState.Error(BaseError()),
            state = FoodDetailUiState.Success(
                FoodDetailModel(
                    foodName = "foodName",
                    image = "",
                    price = 99.0,
                    description = "description",
                    ratingScoreCount = "4.9",
                ),
                true,
            ),
            dispatch = {},
            onBackPressed = {},
            foodId = 0,
        )
    }
}