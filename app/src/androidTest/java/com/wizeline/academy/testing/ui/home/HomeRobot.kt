package com.wizeline.academy.testing.ui.home

import androidx.annotation.StringRes
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.test_utils.FakeMoviesApi
import com.wizeline.academy.testing.test_utils.ScreenRobot
import com.wizeline.academy.testing.test_utils.di.launchFragmentInHiltContainer

class HomeRobot : ScreenRobot<HomeRobot>() {

    fun launch(): HomeRobot {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
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
        val toolbarId = R.id.appbar
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
        val errorViewId = R.id.error_message
        checkIsVisible(errorViewId)
            .checkViewHasText(errorViewId, stringId, *params)
        return this
    }

}