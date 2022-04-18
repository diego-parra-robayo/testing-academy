package com.wizeline.academy.testing.domain

import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(): Result<List<Movie>>
    suspend fun getMovieDetails(id: String): Result<MovieDetails>
    suspend fun getFavorites(): Result<List<String>>
    fun getFavoritesAsFlow(): Flow<List<String>>
    fun isFavoriteAsFlow(movieId: String): Flow<Boolean>
    suspend fun isFavorite(movieId: String): Boolean
    suspend fun addFavorite(movieId: String)
    suspend fun removeFavorite(movieId: String)

}