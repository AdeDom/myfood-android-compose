package com.adedom.main.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adedom.ui_components.components.AppIcon
import com.adedom.ui_components.components.AppTextField

@Composable
fun SearchTextField(
    value: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppTextField(
        value = value,
        onValueChange = onSearchChange,
        hint = "Search food",
        leadingIcon = {
            AppIcon(Icons.Default.Search)
        },
        modifier = modifier,
    )
}