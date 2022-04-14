package com.wizeline.academy.testing.test_utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.enqueueResponse(filename: String, code: Int) {
    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(loadFileContent("api-response/$filename"))
    )
}
