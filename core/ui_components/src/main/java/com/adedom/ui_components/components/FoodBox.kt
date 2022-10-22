package com.adedom.ui_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adedom.ui_components.domain.models.FoodModel
import com.adedom.ui_components.theme.Amber

@Composable
fun FoodBoxItem(
    food: FoodModel,
    onFoodClick: (Long) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
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
                    style = MaterialTheme.typography.subtitle1,
                )
                food.alias?.let { alias ->
                    Spacer(modifier = Modifier.height(4.dp))
                    AppText(
                        text = alias,
                        color = Color.Gray,
                        style = MaterialTheme.typography.body2,
                    )
                }
                food.favorite?.let { favorite ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        AppIcon(
                            image = Icons.Default.Favorite,
                            color = Amber,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        AppText(
                            text = favorite.toString(),
                            color = Amber,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
                food.ratingScoreCount?.let { ratingScoreCount ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        AppIcon(
                            image = Icons.Default.Star,
                            color = Amber,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        AppText(
                            text = ratingScoreCount,
                            color = Amber,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }
}