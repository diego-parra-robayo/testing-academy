package com.wizeline.academy.testing.ui.details

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wizeline.academy.testing.test_utils.di.BaseHiltTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest : BaseHiltTest() {

    @Test
    fun testContent() {
        DetailsRobot()
            .launch(movieId = movieDetails.id)
            .overview(movieDetails.overview)
            .rating(movieDetails.voteAverage)
            .releaseDate(movieDetails.releaseDate)
            .homepageUrl(movieDetails.homepage)
            .isFavorite(false)
    }

}