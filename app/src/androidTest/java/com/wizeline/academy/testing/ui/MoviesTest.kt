package com.wizeline.academy.testing.ui

import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wizeline.academy.testing.MainActivity
import com.wizeline.academy.testing.test_utils.di.BaseHiltTest
import com.wizeline.academy.testing.ui.details.DetailsRobot
import com.wizeline.academy.testing.ui.home.HomeRobot
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MoviesTest : BaseHiltTest() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun openDetailAndBack() {
        HomeRobot().clickMovie(0)
        DetailsRobot().overview(movieDetails.overview)
        Espresso.pressBack()
        HomeRobot().checkMoviesVisible()
    }

}