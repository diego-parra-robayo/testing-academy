package com.wizeline.academy.testing.test_utils

import com.wizeline.academy.testing.data.network.dtos.GenreDto
import com.wizeline.academy.testing.data.network.dtos.MovieDetailsDto
import com.wizeline.academy.testing.data.network.dtos.MovieDto
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MovieDetails

object TestData {

    val movieDto634649 = MovieDto(
        id = 634649,
        title = "Spider-Man: No Way Home",
        posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        releaseDate = "2021-12-15",
        voteAverage = 8.2f
    )

    val movieDetailsDto675353 = MovieDetailsDto(
        id = 675353,
        title = "Sonic the Hedgehog 2",
        posterPath = "/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
        backdropPath = "/egoyMDLqCxzjnSrWOz50uLlJWmD.jpg",
        genres = listOf(
            GenreDto(28, "Action"),
            GenreDto(878, "Science Fiction"),
            GenreDto(35, "Comedy"),
            GenreDto(10751, "Family")
        ),
        overview = "After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero. His test comes when Dr. Robotnik returns, this time with a new partner, Knuckles, in search for an emerald that has the power to destroy civilizations. Sonic teams up with his own sidekick, Tails, and together they embark on a globe-trotting journey to find the emerald before it falls into the wrong hands.",
        homepage = "https://www.sonicthehedgehogmovie.com",
        releaseDate = "2022-03-30",
        voteAverage = 7.8f
    )

}