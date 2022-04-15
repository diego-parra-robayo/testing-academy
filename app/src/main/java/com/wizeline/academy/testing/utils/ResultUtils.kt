package com.wizeline.academy.testing.utils

fun <T> Result<T>.toResource(): Resource<T> =
    this.fold(
        onSuccess = { Resource.Success(it) },
        onFailure = { Resource.Failure(it) }
    )