package com.wizeline.academy.testing.test_utils

import com.wizeline.academy.testing.test_utils.data.LocalTestFileReader
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlin.jvm.Throws

fun MockWebServer.enqueueResponse(
    filename: String,
    code: Int
) {
    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(LocalTestFileReader.loadFileContent(filename))
    )
}