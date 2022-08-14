package com.adedom.food_detail.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.food_detail.R
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
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_star_amber),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeightDp / 2),
            contentScale = ContentScale.Crop,
        )
        Card(
            shape = RoundedCornerShape(
                topStart = 64.dp,
                topEnd = 64.dp,
            ),
            elevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeightDp / 2)
                .align(Alignment.BottomCenter),
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 32.dp,
                )
            ) {
                Text(
                    text = "foodName",
                    color = Color.Black,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star_amber),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "4.9",
                        color = Color(0xFFFFC107),
                        fontSize = 14.sp,
                    )
                    Box(modifier = Modifier.weight(1f))
                    Text(
                        text = "price",
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
                    text = "description",
                    fontSize = 16.sp,
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.favorite_active),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterEnd)
                .padding(bottom = 64.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailContentPreview() {
    MyFoodTheme {
        FoodDetailContent()
    }
}