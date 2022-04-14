package com.wizeline.academy.testing.data

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.testing.data.network.dtos.MovieListResponse
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MovieDetails
import com.wizeline.academy.testing.test_utils.TestData
import org.junit.Test

class MappersImplTest {

    private val mapper = MappersImpl

    private val mappedMovieDto634649 = Movie(
        id = "634649",
        title = "Spider-Man: No Way Home",
        imageUrl = "https://image.tmdb.org/t/p/original/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        releaseDate = "2021-12-15",
        voteAverage = 82
    )
    private val mappedMovieDetailsDto675353Movie = Movie(
        id = "675353",
        title = "Sonic the Hedgehog 2",
        imageUrl = "https://image.tmdb.org/t/p/original/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
        releaseDate = "2022-03-30",
        voteAverage = 78
    )
    private val mappedMovieDetailsDto675353 = MovieDetails(
        id = "675353",
        title = "Sonic the Hedgehog 2",
        imageUrl = "https://image.tmdb.org/t/p/original/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
        backdropUrl = "https://image.tmdb.org/t/p/original/egoyMDLqCxzjnSrWOz50uLlJWmD.jpg",
        genres = listOf("Action", "Science Fiction", "Comedy", "Family"),
        overview = "After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero. His test comes when Dr. Robotnik returns, this time with a new partner, Knuckles, in search for an emerald that has the power to destroy civilizations. Sonic teams up with his own sidekick, Tails, and together they embark on a globe-trotting journey to find the emerald before it falls into the wrong hands.",
        homepage = "https://www.sonicthehedgehogmovie.com",
        releaseDate = "2022-03-30",
        voteAverage = 78
    )

    @Test
    fun toMoviesList() {
        val current = TestData.movieDto634649
        val expected = mappedMovieDto634649
        val movieResponse = MovieListResponse(
            page = 1,
            results = listOf(current),
            totalPages = 1,
            totalResults = 1
        )

        val result = mapper.toMovieList(movieResponse)

        assertThat(result).isEqualTo(listOf(expected))
    }

    @Test
    fun toMoviesListWhenEmptyList() {
        val movieResponse = MovieListResponse(
            page = 1,
            results = emptyList(),
            totalPages = 1,
            totalResults = 1
        )

        val result = mapper.toMovieList(movieResponse)

        assertThat(result).isEmpty()
    }

    @Test
    fun toMovieFromMovieDto() {
        val current = TestData.movieDto634649
        val expected = mappedMovieDto634649
        val result = mapper.toMovie(current)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun toMovieFromMovieDetailsDto() {
        val current = TestData.movieDetailsDto675353
        val expected = mappedMovieDetailsDto675353Movie
        val result = mapper.toMovie(current)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun toMovieDetails() {
        val current = TestData.movieDetailsDto675353
        val expected = mappedMovieDetailsDto675353
        val result = mapper.toMovieDetails(current)
        assertThat(result).isEqualTo(expected)
    }

}