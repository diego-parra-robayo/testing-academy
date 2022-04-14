package com.wizeline.academy.testing.data

import com.wizeline.academy.testing.data.network.dtos.MovieDetailsDto
import com.wizeline.academy.testing.data.network.dtos.MovieDto
import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.data.network.dtos.MovieListResponse
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MovieDetails

object MappersImpl : Mappers {

    override fun toMovieList(dto: MovieListResponse): List<Movie> =
        dto.results?.map { toMovie(it) } ?: emptyList()

    override fun toMovie(dto: MovieDto): Movie = with(dto) {
        Movie(
            id = id.toString(),
            title = title ?: "",
            imageUrl = getImageUrlOrNull(posterPath) ?: "",
            releaseDate = releaseDate ?: "",
            voteAverage = voteAverage?.times(10)?.toInt() ?: 0
        )
    }

    override fun toMovieDetails(dto: MovieDetailsDto): MovieDetails = with(dto) {
        MovieDetails(
            id = id.toString(),
            title = title ?: "",
            imageUrl = getImageUrlOrNull(posterPath) ?: "",
            backdropUrl = getImageUrlOrNull(backdropPath) ?: "",
            genres = dto.genres?.map { it.name.toString() } ?: emptyList(),
            overview = overview ?: "",
            homepage = homepage ?: "",
            releaseDate = releaseDate ?: "",
            voteAverage = voteAverage?.times(10)?.toInt() ?: 0
        )
    }

    private fun getImageUrlOrNull(relativePath: String?): String? =
        relativePath?.let { MoviesApi.IMAGE_URL_PREFIX + it }

}