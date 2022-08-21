package com.adedom.main.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adedom.main.domain.models.FoodModel
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppImageNetwork
import com.adedom.ui_components.components.AppText

@Composable
fun FoodBoxItem(
    food: FoodModel,
    onFoodClick: (Long) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onFoodClick(food.foodId)
            },
    ) {
        Row {
            AppImageNetwork(
                image = food.image,
                modifier = Modifier
                    .size(
                        width = 100.dp,
                        height = 100.dp,
                    )
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                AppText(
                    text = food.foodName,
                    fontWeight = FontWeight.Bold,
                )
                food.alias?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    AppText(
                        text = food.alias,
                        color = Color.Gray,
                        fontSize = 14.sp,
                    )
                }
                food.ratingScoreCount?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        AppIcon(
                            image = Icons.Default.Star,
                            color = Color(0xFFFFC107),
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        AppText(
                            text = food.ratingScoreCount,
                            color = Color(0xFFFFC107),
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}