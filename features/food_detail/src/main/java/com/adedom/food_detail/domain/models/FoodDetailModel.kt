package com.adedom.food_detail.domain.models

data class FoodDetailModel(
    val foodName: String,
    val image: String,
    val price: Double,
    val description: String,
    val favorite: Long,
    val ratingScoreCount: String,
    val isFavorite: Boolean,
    val isFavoriteState: Boolean,
)