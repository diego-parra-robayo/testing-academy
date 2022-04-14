package com.wizeline.academy.testing.data

import com.google.common.truth.Truth.assertThat
import com.wizeline.academy.testing.domain.MoviesRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * This class is more intended to check the MoviesApi, that values are being actually fetch in the
 * ui, and the queries are not lacking any value such as a key
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MoviesRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var moviesRepository: MoviesRepository

    private val existentMovieId = "634649"

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testGetMoviesSuccessful() = runTest {
        val result = moviesRepository.getMovies()
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isNotEmpty()
    }

    @Test
    fun testGetMovieSuccessful() = runTest {
        val result = moviesRepository.getMovie(existentMovieId)
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun testGetMovieDetailsSuccessful() {
        val result = moviesRepository.getMovieDetails(existentMovieId)
        assertThat(result.blockingGet()).isNotNull()
    }

}