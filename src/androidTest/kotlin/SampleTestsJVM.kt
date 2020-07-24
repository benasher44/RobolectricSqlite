package com.example.robolectricSqlite

import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class SampleTestsJVM {
    @Test
    fun testVersion() {
        println(getSqliteVersion(ApplicationProvider.getApplicationContext()))
    }
}