package com.adedom.main.domain.models

import com.adedom.core.domain.models.FoodModel

data class MainContentModel(
    val categories: List<CategoryModel>,
    val foods: List<FoodModel>,
)