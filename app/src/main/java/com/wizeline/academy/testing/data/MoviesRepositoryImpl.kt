package com.wizeline.academy.testing.data

import com.wizeline.academy.testing.data.local.FavoritesDao
import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.di.IoDispatcher
import com.wizeline.academy.testing.di.IoScheduler
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MovieDetails
import com.wizeline.academy.testing.domain.MoviesRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.rx3.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val favoritesDao: FavoritesDao,
    private val mappers: Mappers,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @IoScheduler private val scheduler: Scheduler
) : MoviesRepository {

    override suspend fun getMovies(): Result<List<Movie>> = withContext(dispatcher) {
        runCatching {
            moviesApi.getPopularMovies()
                .let { mappers.toMovieList(it) }
        }
    }

    override suspend fun getMovie(id: String): Result<Movie> = withContext(dispatcher) {
        runCatching {
            moviesApi.getMovie(id)
                .await()
                .let { mappers.toMovie(it) }
        }
    }

    override fun getMovieDetails(id: String): Single<MovieDetails> =
        moviesApi.getMovie(id)
            .map { mappers.toMovieDetails(it) }
            .subscribeOn(scheduler)

    override suspend fun getFavorites(): Result<List<String>> = withContext(dispatcher) {
        runCatching {
            favoritesDao.getFavorites()
        }
    }

    override fun getFavoritesAsFlow(): Flow<List<String>> =
        favoritesDao
            .getFavoritesAsFlow()
            .flowOn(dispatcher)

    override suspend fun isFavorite(movieId: String): Boolean = withContext(dispatcher) {
        favoritesDao.isFavourite(movieId)
    }

    override fun isFavoriteAsFlow(movieId: String): Flow<Boolean> =
        favoritesDao
            .isFavouriteAsFlow(movieId)
            .flowOn(dispatcher)

    override suspend fun addFavorite(movieId: String) = withContext(dispatcher) {
        favoritesDao.addFavorite(movieId)
    }

    override suspend fun removeFavorite(movieId: String) = withContext(dispatcher) {
        favoritesDao.removeFavorite(movieId)
    }

}