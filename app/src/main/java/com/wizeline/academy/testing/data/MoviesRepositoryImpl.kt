package com.wizeline.academy.testing.data

import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.di.IoDispatcher
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MovieDetails
import com.wizeline.academy.testing.domain.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val mappers: Mappers,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MoviesRepository {

    override suspend fun getMovies(): Result<List<Movie>> = withContext(dispatcher) {
        runCatching {
            moviesApi.getPopularMovies()
                .let { mappers.toMovieList(it) }
        }
    }

    override suspend fun getMovieDetails(id: String): Result<MovieDetails> =
        withContext(dispatcher) {
            runCatching {
                moviesApi.getMovie(id)
                    .let { mappers.toMovieDetails(it) }
            }
        }

}