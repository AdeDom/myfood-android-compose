package com.adedom.food_detail.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.food_detail.R
import com.adedom.food_detail.domain.models.FoodDetailModel
import com.adedom.food_detail.presentation.event.FoodDetailUiEvent
import com.adedom.food_detail.presentation.state.FoodDetailUiState
import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import com.adedom.ui_components.components.AppErrorAlertDialog
import com.adedom.ui_components.components.AppImage
import com.adedom.ui_components.components.AppImageNetwork
import com.adedom.ui_components.theme.MyFoodTheme
import org.kodein.di.compose.rememberInstance

@Composable
fun FoodDetailScreen(
    foodId: Int?,
    onEvent: (FoodDetailUiEvent) -> Unit,
) {
    val viewModel by rememberInstance<FoodDetailViewModel>()

    LaunchedEffect(key1 = viewModel) {
        viewModel.callFoodDetail(foodId)
    }

    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }

    val state = viewModel.uiState

    FoodDetailContent(
        state = state,
        onErrorDismiss = viewModel::setOnBackPressedEvent,
    )
}

@Composable
fun FoodDetailContent(
    state: FoodDetailUiState,
    onErrorDismiss: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }

        if (state.error != null) {
            AppErrorAlertDialog(
                error = state.error,
                onDismiss = onErrorDismiss,
            )
        }

        state.foodDetail?.let { foodDetail ->
            AppImageNetwork(
                image = foodDetail.image,
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
                                        text = foodDetail.foodName,
                                        color = Color.Black,
                                        fontSize = 24.sp,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        verticalAlignment = Alignment.Bottom,
                                    ) {
                                        AppImage(
                                            image = R.drawable.ic_star_amber,
                                            modifier = Modifier.size(18.dp),
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = foodDetail.ratingScoreCount,
                                            color = Color(0xFFFFC107),
                                            fontSize = 14.sp,
                                        )
                                        Box(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "${foodDetail.price}",
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
                                        text = foodDetail.description,
                                        fontSize = 16.sp,
                                    )
                                }
                            }
                        }
                        AppImage(
                            image = R.drawable.favorite_active,
                            modifier = Modifier
                                .size(80.dp)
                                .offset(
                                    x = screenWidthDp - 128.dp,
                                    y = 16.dp,
                                ),
                        )
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
            state = FoodDetailUiState(
                foodDetail = FoodDetailModel(
                    foodName = "foodName",
                    image = "",
                    price = 99.0,
                    description = "description",
                    ratingScoreCount = "4.9",
                )
            ),
            onErrorDismiss = {},
        )
    }
}