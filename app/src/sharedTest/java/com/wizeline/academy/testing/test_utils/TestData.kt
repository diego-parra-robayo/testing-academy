package com.wizeline.academy.testing.test_utils

import com.wizeline.academy.testing.data.network.dtos.MovieDetailsDto
import com.wizeline.academy.testing.data.network.dtos.MovieDto
import com.wizeline.academy.testing.data.network.dtos.MovieListResponse

open class TestData(private val fileReader: FileReader) {

    val popularMovies =
        fileReader.loadJsonFromFile<MovieListResponse>(POPULAR_MOVIES_200)

    val movieDetails675353 =
        fileReader.loadJsonFromFile<MovieDetailsDto>(MOVIE_DETAILS_675353_200)

    val movie634649 =
        fileReader.loadJsonFromFile<MovieDto>(MOVIE_634649)

    val moviesListTotal3 =
        fileReader.loadJsonFromFile<MovieListResponse>(MOVIES_LIST_TOTAL_3)

    val moviesList1st414906 =
        fileReader.loadJsonFromFile<MovieDetailsDto>(MOVIES_LIST_1ST_414906)
    val moviesList2nd634649 =
        fileReader.loadJsonFromFile<MovieDetailsDto>(MOVIES_LIST_2ND_634649)
    val moviesList3rd799876 =
        fileReader.loadJsonFromFile<MovieDetailsDto>(MOVIES_LIST_3RD_799876)


    companion object ResFilenames {

        const val POPULAR_MOVIES_200 = "popular-movies-200.json"
        const val MOVIE_DETAILS_675353_200 = "movie-details-675353-200.json"
        const val MOVIE_634649 = "movie-634649.json"

        const val ERROR_RESPONSE_401 = "401-response.json"
        const val ERROR_RESPONSE_404 = "404-response.json"

        const val MOVIES_LIST_TOTAL_3 = "movies-list-total-3.json"
        const val MOVIES_LIST_1ST_414906 = "movies-list-1st-414906.json"
        const val MOVIES_LIST_2ND_634649 = "movies-list-2nd-634649.json"
        const val MOVIES_LIST_3RD_799876 = "movies-list-3rd-799876.json"

    }
}
