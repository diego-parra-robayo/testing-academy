package com.wizeline.academy.testing.test_utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import okio.IOException
import kotlin.jvm.Throws

@Throws(IOException::class, JsonSyntaxException::class)
inline fun <reified T> loadJsonFromResFile(filename: String): T {
    val contentString = loadFileContent(filename)
    return loadJsonFromString(contentString)
}

@Throws(JsonSyntaxException::class)
inline fun <reified T> loadJsonFromString(jsonString: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(jsonString, type)
}

@Throws(IOException::class)
fun loadFileContent(filename: String): String =
    ClassLoader.getSystemResourceAsStream(filename)
        .bufferedReader()
        .use { it.readText() }
