package com.wizeline.academy.testing.test_utils.matchers

import android.view.View
import androidx.annotation.StringRes
import org.hamcrest.Matcher

object CustomMatchers {

    fun withText(@StringRes resourceId: Int, vararg params: Any): Matcher<View> {
        return WithTextMatcher(resourceId, params)
    }

    fun withTag(url: String): Matcher<View> {
        return ImageWithTagMatcher(url)
    }

    fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return ItemAtPositionMatcher(position, itemMatcher)
    }

}