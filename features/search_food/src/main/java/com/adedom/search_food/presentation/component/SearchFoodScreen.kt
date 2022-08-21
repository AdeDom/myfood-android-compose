package com.adedom.search_food.presentation.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.adedom.search_food.presentation.view_model.SearchFoodViewModel
import com.adedom.ui_components.theme.MyFoodTheme
import org.kodein.di.compose.rememberInstance

@Composable
fun SearchFoodScreen() {
    val viewModel: SearchFoodViewModel by rememberInstance()

    SearchFoodContent()
}

@Composable
fun SearchFoodContent() {
    Text(text = "Hello search food")
}

@Composable
@Preview(showBackground = true)
fun SearchFoodContentPreview() {
    MyFoodTheme {
        SearchFoodScreen()
    }
}