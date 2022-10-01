package com.adedom.user_profile.domain.models

data class UserProfileModel(
    val userId: String,
    val name: String,
    val email: String,
    val image: String,
    val address: String,
    val mobileNo: String,
)