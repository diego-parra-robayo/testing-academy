package com.wizeline.academy.testing.ui.details

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.test_utils.ScreenRobot
import com.wizeline.academy.testing.test_utils.di.launchFragmentInHiltContainer

class DetailsRobot : ScreenRobot<DetailsRobot>() {

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    private val toolbarId = R.id.collapsing_toolbar
    private val toolbarImageId = R.id.appbar_image
    private val ratingBarId = R.id.rating_bar
    private val ratingTextId = R.id.rating_text
    private val detailsId = R.id.details
    private val overviewId = R.id.overview
    private val releaseDateId = R.id.release_date
    private val homepageUrlId = R.id.homepage_url
    private val btnFavoriteId = R.id.btn_favorite
    private val errorViewId = R.id.error_message

    fun launch(movieId: String): DetailsRobot {
        launchFragmentInHiltContainer<DetailsFragment>(
            fragmentArgs = bundleOf(DetailsViewModel.MOVIE_ID_SAVED_STATE_KEY to movieId),
            beforeTransaction = {
                this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.nav_main)
                        Navigation.setViewNavController(this.requireView(), navController)
                    }
                }
            })
        return this
    }

    fun title(str: String): DetailsRobot {
        checkIsVisible(toolbarId)
            .checkToolbarHasTitle(toolbarId, str)
        return this
    }

    fun checkError(@StringRes stringId: Int, vararg params: Any): DetailsRobot {
        checkIsVisible(errorViewId)
            .checkViewHasText(errorViewId, stringId, *params)
        return this
    }

    fun rating(score: Int): DetailsRobot {
        checkViewHasText(ratingTextId, "($score%)")
        return this
    }

    fun overview(str: String): DetailsRobot {
        checkViewHasText(overviewId, str)
        return this
    }

    fun releaseDate(str: String): DetailsRobot {
        checkViewHasText(releaseDateId, str)
        return this
    }

    fun homepageUrl(url: String): DetailsRobot {
        checkViewHasText(homepageUrlId, url)
        return this
    }

    fun isFavorite(boolean: Boolean): DetailsRobot {
        checkViewIsSelected(btnFavoriteId, boolean)
        return this
    }

}