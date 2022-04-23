package com.wizeline.academy.testing.test_utils

import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.data.network.dtos.MovieDetailsDto
import com.wizeline.academy.testing.data.network.dtos.MovieListResponse
import com.wizeline.academy.testing.test_utils.data.AndroidTestData
import io.reactivex.rxjava3.core.Single

object FakeMoviesApi : MoviesApi {

    override suspend fun getPopularMovies(): MovieListResponse {
        return AndroidTestData.moviesListTotal3
    }

    override fun getMovie(movieId: String): Single<MovieDetailsDto> {
        val movie1 = AndroidTestData.moviesList1st414906
        val movie2 = AndroidTestData.moviesList2nd634649
        val movie3 = AndroidTestData.moviesList3rd799876
        val movie = when (movieId) {
            movie1.id.toString() -> movie1
            movie2.id.toString() -> movie2
            movie3.id.toString() -> movie3
            else -> null
        }
        return movie?.let { Single.just(it) }
            ?: Single.error(IllegalArgumentException("No movie was found with id = $movieId"))
    }

}