package com.adedom.showkase

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.annotation.ShowkaseRootModule
import com.airbnb.android.showkase.ui.ShowkaseBrowserActivity

@ShowkaseRoot
class AppRootModule : ShowkaseRootModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            ShowkaseBrowserActivity.getIntent(
                this,
                AppRootModule::class.qualifiedName.toString(),
            ),
        )
        finish()
    }
}