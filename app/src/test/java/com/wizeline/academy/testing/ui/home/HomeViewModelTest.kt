package com.wizeline.academy.testing.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.testing.data.MappersImpl
import com.wizeline.academy.testing.domain.MoviesRepository
import com.wizeline.academy.testing.test_utils.MainCoroutineRule
import com.wizeline.academy.testing.test_utils.data.LocalTestData
import com.wizeline.academy.testing.test_utils.getOrAwaitValue
import com.wizeline.academy.testing.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var viewModel: HomeViewModel

    private val mappers = MappersImpl


    @Before
    fun setUp() {
        viewModel = HomeViewModel(moviesRepository)
    }

    @Test
    fun filterOptions() {
        assertThat(viewModel.filterOptions).containsExactly(
            FilterOption.ALL,
            FilterOption.FAVORITES
        )
    }

    @Test
    fun selectFilter() = runTest {
        val newFilter = FilterOption.FAVORITES
        viewModel.selectFilter(newFilter)

        val filterValue = viewModel.filter.first()
        assertThat(filterValue).isEqualTo(newFilter)
    }

    @Test
    fun moviesGetAll() = runTest {
        val movies = listOf(LocalTestData.movie634649).map { mappers.toMovie(it) }
        whenever(moviesRepository.getMovies()).thenReturn(Result.success(movies))

        viewModel.selectFilter(FilterOption.ALL)
        val result = viewModel.movies.getOrAwaitValue()

        assertThat((result as? Resource.Success)?.data).isEqualTo(movies)
    }

    @Test
    fun moviesGetFavorites() = runTest {
        val movie = mappers.toMovie(LocalTestData.movieDetails675353)
        whenever(moviesRepository.getFavorites()).thenReturn(Result.success(listOf(movie.id)))
        whenever(moviesRepository.getMovie(movie.id)).thenReturn(Result.success(movie))

        viewModel.selectFilter(FilterOption.FAVORITES)
        val result = viewModel.movies.getOrAwaitValue()

        assertThat((result as? Resource.Success)?.data).isEqualTo(listOf(movie))
    }

    @Test
    fun onMovieClickThenSetNewNavigationEvent() {
        val movieId = mappers.toMovie(LocalTestData.movieDetails675353).id
        viewModel.onMovieClick(movieId)

        val value = viewModel.navigateDetails.getOrAwaitValue()
        val content = value.getContentIfNotHandled()
        assertThat(content).isNotNull()
        assertThat(content).isEqualTo(movieId)
    }
}