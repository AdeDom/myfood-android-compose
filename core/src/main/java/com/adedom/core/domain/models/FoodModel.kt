package com.adedom.core.domain.models

data class FoodModel(
    val foodId: Long,
    val foodName: String,
    val alias: String?,
    val image: String,
    val ratingScoreCount: String?,
    val categoryId: Long?,
)