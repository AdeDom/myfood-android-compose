package com.adedom.ui.components.domain.models

data class FoodModel(
    val foodId: Long,
    val foodName: String,
    val alias: String?,
    val image: String,
    val favorite: Long?,
    val ratingScoreCount: String?,
    val categoryId: Long?
)
