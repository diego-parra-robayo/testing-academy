package com.wizeline.academy.testing.ui.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wizeline.academy.testing.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testTitle() {
        HomeRobot()
            .launch()
            .title(R.string.app_name)
    }

}