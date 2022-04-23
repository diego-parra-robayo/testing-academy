package com.wizeline.academy.testing.test_utils

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.wizeline.academy.testing.test_utils.matchers.CustomMatchers

abstract class ScreenRobot<out T> {

    fun checkIsVisible(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            onView(withId(viewId)).check(matches(isDisplayed()))
        }
        return this as T
    }

    fun checkToolbarHasTitle(@IdRes toolbarId: Int, @StringRes stringId: Int): T {
        onView(withId(toolbarId)).check(matches(hasDescendant(withText(stringId))))
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, @StringRes stringId: Int, vararg params: Any): T {
        onView(withId(viewId)).check(matches(CustomMatchers.withText(stringId, *params)))
        return this as T
    }

}