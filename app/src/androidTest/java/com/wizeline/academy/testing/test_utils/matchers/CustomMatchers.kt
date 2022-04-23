package com.wizeline.academy.testing.test_utils.matchers

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object CustomMatchers {

    fun withText(@StringRes resourceId: Int, vararg params: Any): Matcher<View> {
        return WithTextMatcher(resourceId, params)
    }


    private class WithTextMatcher(
        @StringRes private val resourceId: Int,
        private val params: Array<out Any>
    ) :
        BoundedMatcher<View, TextView>(TextView::class.java) {

        override fun describeTo(description: Description?) {
            description?.appendText("view.getText() equals string from resource id: $resourceId with params: $params")
        }

        override fun matchesSafely(item: TextView?): Boolean {
            if (item == null) return false
            return item.text == item.resources.getString(resourceId, *params)
        }
    }

}