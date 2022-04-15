package com.wizeline.academy.testing.utils

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    class Success<T>(val data: T) : Resource<T>()
    class Failure(val error: Throwable) : Resource<Nothing>()
}