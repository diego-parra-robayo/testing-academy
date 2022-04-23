package com.wizeline.academy.testing.test_utils.data

import com.wizeline.academy.testing.test_utils.FileReader

object LocalTestFileReader : FileReader() {
    override fun loadFileContent(filename: String): String =
        ClassLoader.getSystemResourceAsStream(filename)
            .bufferedReader()
            .use { it.readText() }
}