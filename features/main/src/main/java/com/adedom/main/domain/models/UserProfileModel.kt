package com.adedom.main.domain.models

data class UserProfileModel(
    val userId: String,
    val email: String,
    val name: String,
    val mobileNo: String,
    val address: String,
    val image: String,
)