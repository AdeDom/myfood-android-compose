package com.adedom.showkase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.airbnb.android.showkase.ui.ShowkaseBrowserActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            startActivity(
                ShowkaseBrowserActivity.getIntent(
                    this,
                    AppRootModule::class.qualifiedName.toString(),
                ),
            )
            finish()
        }
    }
}