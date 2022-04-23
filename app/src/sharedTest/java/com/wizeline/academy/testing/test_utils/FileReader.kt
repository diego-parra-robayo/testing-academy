package com.wizeline.academy.testing.test_utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class FileReader {

    abstract fun loadFileContent(filename: String): String

    inline fun <reified T> loadJsonFromFile(filename: String): T {
        val contentString = loadFileContent(filename)
        return loadJsonFromString(contentString)
    }

    inline fun <reified T> loadJsonFromString(jsonString: String): T {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}