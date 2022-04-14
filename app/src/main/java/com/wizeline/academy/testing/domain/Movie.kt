package com.wizeline.academy.testing.domain

import androidx.annotation.IntRange

data class Movie(
    val id: String,
    val title: String,
    val imageUrl: String,
    val releaseDate: String,
    @IntRange(from = 0, to = 100) val voteAverage: Int
)