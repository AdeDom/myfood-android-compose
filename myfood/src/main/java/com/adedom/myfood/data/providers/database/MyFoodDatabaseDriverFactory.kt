package com.adedom.myfood.data.providers.database

import android.content.Context
import com.adedom.myfood.MyFoodDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class MyFoodDatabaseDriverFactory(private val context: Context) {

    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MyFoodDatabase.Schema, context, "MyFood.db")
    }
}