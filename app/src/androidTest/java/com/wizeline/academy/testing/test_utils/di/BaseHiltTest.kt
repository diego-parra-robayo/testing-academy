package com.wizeline.academy.testing.test_utils.di

import com.wizeline.academy.testing.data.local.TestingDatabase
import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.test_utils.FakeMoviesApi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
abstract class BaseHiltTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: TestingDatabase

    @Inject
    lateinit var moviesApi: MoviesApi

    val fakeMoviesApi get() = moviesApi as FakeMoviesApi


    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun closeDb() {
        database.close()
    }

}