package com.adedom.ui.components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adedom.ui.components.R
import com.adedom.ui.components.domain.models.FoodModel
import com.adedom.ui.components.theme.AppColor
import com.adedom.ui.components.theme.MyFoodTheme
import com.adedom.ui.components.theme.RectangleMediumShape

@Composable
fun FoodBoxItem(
    food: FoodModel,
    onFoodClick: (Long) -> Unit
) {
    Card(
        shape = RectangleMediumShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onFoodClick(food.foodId)
            }
    ) {
        Row {
            AppImage(
                image = food.image,
                contentDescription = stringResource(id = R.string.cd_food_image),
                modifier = Modifier
                    .size(100.dp)
                    .clip(RectangleMediumShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                AppText(
                    text = food.foodName,
                    style = MaterialTheme.typography.titleLarge
                )
                food.alias?.let { alias ->
                    Spacer(modifier = Modifier.height(4.dp))
                    AppText(
                        text = alias,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                food.favorite?.let { favorite ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        AppIcon(
                            image = Icons.Default.Favorite,
                            color = AppColor.Amber,
                            modifier = Modifier.size(18.dp),
                            contentDescription = stringResource(id = R.string.cd_icon_favorite)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        AppText(
                            text = favorite.toString(),
                            color = AppColor.Amber,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                food.ratingScoreCount?.let { ratingScoreCount ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        AppIcon(
                            image = Icons.Default.Star,
                            color = AppColor.Amber,
                            modifier = Modifier.size(18.dp),
                            contentDescription = stringResource(id = R.string.cd_icon_star)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        AppText(
                            text = ratingScoreCount,
                            color = AppColor.Amber,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Food item",
    group = "Component - FoodBox",
    showBackground = true
)
@Composable
fun FoodBoxItemPreview() {
    MyFoodTheme {
        FoodBoxItem(
            food = FoodModel(
                foodId = 1,
                foodName = "foodName",
                alias = "alias",
                image = "",
                favorite = 5,
                ratingScoreCount = "ratingScoreCount",
                categoryId = 2
            ),
            onFoodClick = {}
        )
    }
}
