package com.adedom.search_food.di

import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val featureSearchFoodModule = DI.Module(name = "featureSearchFoodModule") {

    // data

    // domain

    // presentation
    bindProvider { SearchFoodViewModel() }
}