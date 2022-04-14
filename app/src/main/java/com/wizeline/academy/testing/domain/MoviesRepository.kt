package com.wizeline.academy.testing.domain

interface MoviesRepository {

    suspend fun getMovies(): Result<List<Movie>>
    suspend fun getMovieDetails(id: String): Result<MovieDetails>
    
}