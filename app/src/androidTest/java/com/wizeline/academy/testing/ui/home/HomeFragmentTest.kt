package com.wizeline.academy.testing.ui.home

import com.wizeline.academy.testing.test_utils.FakeMoviesApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.data.local.FavoritesDao
import com.wizeline.academy.testing.data.local.TestingDatabase
import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.di.LocalModule
import com.wizeline.academy.testing.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(NetworkModule::class, LocalModule::class)
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: TestingDatabase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testTitle() {
        HomeRobot()
            .launch()
            .title(R.string.app_name)
    }



    @Module
    @InstallIn(SingletonComponent::class)
    object LocalTestModule {
        @Singleton
        @Provides
        fun providesRoomDb(): TestingDatabase {
            return Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    TestingDatabase::class.java
                )
                .allowMainThreadQueries()
                .build()
        }

        @Singleton
        @Provides
        fun providesFavoritesDao(testingDatabase: TestingDatabase): FavoritesDao {
            return testingDatabase.favoritesDao()
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkTestModule {
        @Singleton
        @Provides
        fun providesMoviesApi(): MoviesApi = FakeMoviesApi
    }

}