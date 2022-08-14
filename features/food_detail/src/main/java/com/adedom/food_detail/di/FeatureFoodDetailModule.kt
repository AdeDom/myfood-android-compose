package com.adedom.food_detail.di

import com.adedom.food_detail.presentation.view_model.FoodDetailViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val featureFoodDetailModule = DI.Module(name = "featureFoodDetailModule") {

    // data

    // domain

    // view model
    bindProvider { FoodDetailViewModel() }
}