package com.adedom.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Popup

@Composable
fun AppOnlineNetworkPopup(
    modifier: Modifier = Modifier,
) {
    Popup {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Green),
        ) {
            AppText(
                text = "Online",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
fun AppOfflineNetworkPopup(
    modifier: Modifier = Modifier,
) {
    Popup {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Red),
        ) {
            AppText(
                text = "Offline",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}