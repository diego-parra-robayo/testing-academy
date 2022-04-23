package com.wizeline.academy.testing.test_utils.data

import androidx.test.platform.app.InstrumentationRegistry
import com.wizeline.academy.testing.test_utils.FileReader

object AndroidTestFileReader : FileReader() {
    override fun loadFileContent(filename: String): String =
        InstrumentationRegistry.getInstrumentation().context
            .assets
            .open(filename)
            .bufferedReader()
            .use { it.readText() }
}