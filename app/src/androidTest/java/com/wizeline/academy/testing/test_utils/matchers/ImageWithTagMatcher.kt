package com.wizeline.academy.testing.test_utils.matchers

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class ImageWithTagMatcher(
    private val url: String
) : BoundedMatcher<View, ImageView>(ImageView::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("imageView.getTag() equals url: $url")
    }

    override fun matchesSafely(item: ImageView?): Boolean {
        if (item == null) return false
        return item.tag == url
    }

}