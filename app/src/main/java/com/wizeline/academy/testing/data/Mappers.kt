package com.wizeline.academy.testing.data

import com.wizeline.academy.testing.data.network.dtos.MovieDetailsDto
import com.wizeline.academy.testing.data.network.dtos.MovieDto
import com.wizeline.academy.testing.data.network.dtos.MovieListResponse
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MovieDetails

interface Mappers {

    fun toMovieList(dto: MovieListResponse): List<Movie>
    fun toMovie(dto: MovieDto): Movie
    fun toMovie(dto: MovieDetailsDto): Movie
    fun toMovieDetails(dto: MovieDetailsDto): MovieDetails

}