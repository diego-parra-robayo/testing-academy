package com.wizeline.academy.testing.domain

import androidx.annotation.IntRange

data class MovieDetails(
    val id: String,
    val title: String,
    val imageUrl: String,
    val backdropUrl: String,
    val genres: List<String>,
    val overview: String,
    val homepage: String,
    val releaseDate: String,
    @IntRange(from = 0, to = 100) val voteAverage: Int
)