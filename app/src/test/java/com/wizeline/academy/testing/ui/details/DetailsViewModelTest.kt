package com.wizeline.academy.testing.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.testing.data.MappersImpl
import com.wizeline.academy.testing.domain.MoviesRepository
import com.wizeline.academy.testing.test_utils.MainCoroutineRule
import com.wizeline.academy.testing.test_utils.SchedulersRule
import com.wizeline.academy.testing.test_utils.TestData
import com.wizeline.academy.testing.test_utils.getOrAwaitValue
import com.wizeline.academy.testing.utils.Resource
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    val schedulersRule = SchedulersRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var viewModel: DetailsViewModel

    private val mapper = MappersImpl
    private val movie = mapper.toMovieDetails(TestData.movieDetailsDto675353)

    @Before
    fun setUp() {
        val savedStateHandle =
            SavedStateHandle(mapOf(DetailsViewModel.MOVIE_ID_SAVED_STATE_KEY to movie.id))
        whenever(moviesRepository.getMovieDetails(movie.id)).thenReturn(Single.just(movie))
        viewModel = DetailsViewModel(moviesRepository, savedStateHandle)
    }

    @Test
    fun movieDetails() {
        val result = viewModel.movieDetails.getOrAwaitValue()
        assertThat((result as? Resource.Success)?.data).isEqualTo(movie)
    }

    @Test
    fun toggleFavoriteWhenItWasFavorite() = runTest {
        val initialState = true
        whenever(moviesRepository.isFavorite(movie.id)).thenReturn(initialState)
        viewModel.toggleFavorite()
        verify(moviesRepository).removeFavorite(movie.id)
    }

    @Test
    fun toggleFavoriteWhenItWasNotFavorite() = runTest {
        val initialState = false
        whenever(moviesRepository.isFavorite(movie.id)).thenReturn(initialState)
        viewModel.toggleFavorite()
        verify(moviesRepository).addFavorite(movie.id)
    }

}