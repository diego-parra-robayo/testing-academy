package com.wizeline.academy.testing.data.network.dtos

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Long?,
    val title: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Float?
)