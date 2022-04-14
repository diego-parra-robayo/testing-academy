package com.wizeline.academy.testing.data.network

import com.wizeline.academy.testing.data.network.dtos.MovieDetailsDto
import com.wizeline.academy.testing.data.network.dtos.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: String): MovieDetailsDto


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/original"
    }

}