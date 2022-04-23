package com.wizeline.academy.testing.ui.home

import androidx.annotation.StringRes
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.test_utils.ScreenRobot
import com.wizeline.academy.testing.test_utils.launchFragmentInHiltContainer

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
        return checkIsVisible(R.id.appbar)
            .checkToolbarHasTitle(R.id.appbar, stringId)
    }

}