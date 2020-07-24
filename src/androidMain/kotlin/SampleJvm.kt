package com.example.robolectricSqlite

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory

actual class Sample {
    actual fun checkMe() = 42
}

actual object Platform {
    actual fun name(): String = "JVM"
}

fun getSqliteVersion(context: Context): String {
    val callback = object : SupportSQLiteOpenHelper.Callback(1) {
        override fun onCreate(db: SupportSQLiteDatabase) {

        }

        override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {

        }
    }
    val config = SupportSQLiteOpenHelper
            .Configuration
            .builder(context)
            .name("test.db")
            .callback(callback)
            .build()
    val helper = RequerySQLiteOpenHelperFactory().create(config)
    return helper.readableDatabase.query("SELECT sqlite_version()").use {
        it.moveToNext()
        it.getString(0)
    }
}