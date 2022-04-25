package com.wizeline.academy.testing.test_utils.di

import com.wizeline.academy.testing.data.MappersImpl
import com.wizeline.academy.testing.data.local.TestingDatabase
import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.test_utils.FakeMoviesApi
import com.wizeline.academy.testing.test_utils.data.AndroidTestData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
abstract class BaseHiltTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: TestingDatabase

    @Inject
    lateinit var moviesApi: MoviesApi

    protected val fakeMoviesApi get() = moviesApi as FakeMoviesApi
    protected val itemPosition = 0
    private val movieDto = AndroidTestData.moviesList1st414906
    protected val movie = MappersImpl.toMovie(movieDto)
    protected val movieDetails = MappersImpl.toMovieDetails(movieDto)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun closeDb() {
        database.close()
    }

}