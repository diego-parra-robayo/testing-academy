package com.wizeline.academy.testing.ui.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.data.MappersImpl
import com.wizeline.academy.testing.test_utils.data.AndroidTestData
import com.wizeline.academy.testing.test_utils.di.BaseHiltTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest : BaseHiltTest() {

    @Test
    fun testTitle() {
        HomeRobot()
            .launch()
            .title(R.string.app_name)
    }

    @Test
    fun testError() {
        val errorMessage = "Default error"
        HomeRobot()
            .setError(fakeMoviesApi, Exception(errorMessage))
            .launch()
            .checkError(R.string.default_error_message, errorMessage)
    }

    @Test
    fun testMoviesListIsVisible() {
        HomeRobot()
            .launch()
            .checkMoviesVisible()
    }

    @Test
    fun checkFirstMovieInList() {
        val firstMovie = MappersImpl.toMovie(AndroidTestData.moviesList1st414906)
        HomeRobot()
            .launch()
            .checkMovie(0, firstMovie.imageUrl)
    }

    @Test
    fun selectListItemNavigatesDetailView() {
        HomeRobot()
            .launch()
            .clickMovie(0)
            .navigatesTo(R.id.detailsFragment)
    }


}