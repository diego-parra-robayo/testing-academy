package com.wizeline.academy.testing.data

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.testing.data.local.FavoritesDao
import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.data.network.dtos.MovieListResponse
import com.wizeline.academy.testing.test_utils.TestData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryImplTest {

    @Mock
    private lateinit var moviesApi: MoviesApi

    @Mock
    private lateinit var favoritesDao: FavoritesDao

    private val mappers = MappersImpl

    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        repository = MoviesRepositoryImpl(
            moviesApi = moviesApi,
            favoritesDao = favoritesDao,
            mappers = mappers,
            dispatcher = UnconfinedTestDispatcher(),
            scheduler = Schedulers.trampoline()
        )
    }

    @Test
    fun getMovies() = runTest {
        val popularMovies = listOf(TestData.movieDto634649)
        val expected = popularMovies.map { mappers.toMovie(it) }

        whenever(moviesApi.getPopularMovies())
            .thenReturn(
                MovieListResponse(
                    page = 1,
                    results = popularMovies,
                    totalPages = 1,
                    totalResults = 1
                )
            )

        val result = repository.getMovies()
        assertThat(result.getOrNull()).isEqualTo(expected)
    }

    @Test
    fun getMovie() = runTest {
        val movie = TestData.movieDetailsDto675353
        val movieId = movie.id.toString()
        val expected = mappers.toMovie(movie)
        whenever(moviesApi.getMovie(movieId = movieId)).thenReturn(Single.just(movie))

        val result = repository.getMovie(movieId)
        assertThat(result.getOrNull()).isEqualTo(expected)
    }

    @Test
    fun getMovieDetails() {
        val movie = TestData.movieDetailsDto675353
        val movieId = movie.id.toString()
        val expected = mappers.toMovieDetails(movie)
        whenever(moviesApi.getMovie(movieId)).thenReturn(Single.just(movie))

        val result = repository.getMovieDetails(movieId)
        assertThat(result.blockingGet()).isEqualTo(expected)
    }

    @Test
    fun getFavorites() = runTest {
        val favorites = listOf("movieId1", "movieId2", "movieId3")
        whenever(favoritesDao.getFavorites()).thenReturn(favorites)

        val result = repository.getFavorites()
        assertThat(result.getOrNull()).isEqualTo(favorites)
    }

    @Test
    fun getFavoritesAsFlow() = runTest {
        val favorites = listOf("movieId1", "movieId2", "movieId3")
        whenever(favoritesDao.getFavoritesAsFlow()).thenReturn(flowOf(favorites))

        val result = repository.getFavoritesAsFlow()
        assertThat(result.first()).isEqualTo(favorites)
    }

    @Test
    fun isFavorite() = runTest {
        val movieId = "movieId"
        whenever(favoritesDao.isFavourite(movieId)).thenReturn(true)

        val result = repository.isFavorite(movieId)
        assertThat(result).isTrue()
    }

    @Test
    fun isFavoriteAsFlow() = runTest {
        val movieId = "movieId"
        whenever(favoritesDao.isFavouriteAsFlow(movieId)).thenReturn(flowOf(true))

        val result = repository.isFavoriteAsFlow(movieId)
        assertThat(result.first()).isTrue()
    }

    @Test
    fun addFavorite() = runTest {
        val movieId = "movieId"
        repository.addFavorite(movieId)
        verify(favoritesDao).addFavorite(movieId)
        //  Note: Requires mockito-inline: In order to mock final classes and methods.
        //  As addFavorite is a final method, it will not be considered by mockito-core as the
        //  insert abstract method, which can be easily mocked.
    }

    @Test
    fun removeFavorite() = runTest {
        val movieId = "movieId"
        repository.removeFavorite(movieId)
        verify(favoritesDao).removeFavorite(movieId)
    }
}