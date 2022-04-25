package com.wizeline.academy.testing.ui.home

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.common.truth.Truth
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.test_utils.FakeMoviesApi
import com.wizeline.academy.testing.test_utils.ScreenRobot
import com.wizeline.academy.testing.test_utils.di.launchFragmentInHiltContainer
import com.wizeline.academy.testing.test_utils.matchers.CustomMatchers

class HomeRobot : ScreenRobot<HomeRobot>() {

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    private val toolbarId = R.id.appbar
    private val errorViewId = R.id.error_message
    private val moviesId = R.id.movies_list

    fun launch(): HomeRobot {
        launchFragmentInHiltContainer<HomeFragment>(beforeTransaction = {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    navController.setGraph(R.navigation.nav_main)
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        })
        return this
    }

    fun title(@StringRes stringId: Int): HomeRobot {
        checkIsVisible(toolbarId)
            .checkToolbarHasTitle(toolbarId, stringId)
        return this
    }

    fun setError(
        api: FakeMoviesApi,
        throwable: Throwable? = Exception("Default error")
    ): HomeRobot {
        api.error = throwable
        return this
    }

    fun checkError(@StringRes stringId: Int, vararg params: Any): HomeRobot {
        checkIsVisible(errorViewId)
            .checkViewHasText(errorViewId, stringId, *params)
        return this
    }

    fun checkMoviesVisible(): HomeRobot {
        checkIsVisible(moviesId)
        return this
    }

    fun checkMovie(position: Int, url: String): HomeRobot {
        onView(withId(moviesId))
            .check(
                matches(
                    CustomMatchers.atPosition(position, hasDescendant(CustomMatchers.withTag(url)))
                )
            )
        return this
    }

    fun clickMovie(position: Int): HomeRobot {
        onView(withId(moviesId))
            .perform(actionOnItemAtPosition<MoviesAdapter.ViewHolder>(position, click()))
        return this
    }

    fun navigatesTo(@IdRes destinationId: Int): HomeRobot {
        Truth.assertThat(navController.currentDestination?.id).isEqualTo(destinationId)
        return this
    }

}