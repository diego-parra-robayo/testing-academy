package com.wizeline.academy.testing.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavoritesDaoTest {

    private lateinit var database: TestingDatabase
    private lateinit var dao: FavoritesDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TestingDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()
        dao = database.favoritesDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertFavoriteWhenDatabaseIsEmpty() = runTest {
        val currentFavorites = dao.getFavorites()
        assertThat(currentFavorites).isEmpty()
        val movieId = "movieId"

        dao.addFavorite(movieId)

        val result = dao.getFavorites()
        assertThat(result).isEqualTo(listOf(movieId))
    }

    @Test
    fun insertFavoriteAlreadyInDatabaseDoesNotCauseDuplicates() = runTest {
        val movieId = "movieId"
        dao.addFavorite(movieId)
        assertThat(dao.getFavorites()).isEqualTo(listOf(movieId))

        dao.addFavorite(movieId)
        val result = dao.getFavorites()
        assertThat(result).isEqualTo(listOf(movieId))
        assertThat(result).containsNoDuplicates()
    }

    @Test
    fun deleteFavorite() = runTest {
        val movieId1 = "movieId1"
        val movieId2 = "movieId2"
        dao.addFavorite(movieId1)
        dao.addFavorite(movieId2)
        assertThat(dao.getFavorites()).isEqualTo(listOf(movieId1, movieId2))

        dao.removeFavorite(movieId1)
        assertThat(dao.getFavorites()).isEqualTo(listOf(movieId2))
    }

    @Test
    fun deleteNonExistentFavoriteDoesNoting() = runTest {
        val movieId = "movieId"
        dao.addFavorite(movieId)
        assertThat(dao.getFavorites()).isEqualTo(listOf(movieId))

        dao.removeFavorite("nonExistentMovieId")
        assertThat(dao.getFavorites()).isEqualTo(listOf(movieId))
    }

    @Test
    fun getFavoritesAsFlowWhenFavoriteIsAddedAndThenRemoved() = runTest {
        val movieId = "movieId"
        assertThat(dao.getFavorites()).isEmpty()

        val favoritesFlow = dao.getFavoritesAsFlow()
        assertThat(favoritesFlow.first()).isEmpty()

        dao.addFavorite(movieId)
        assertThat(favoritesFlow.first()).isEqualTo(listOf(movieId))

        dao.removeFavorite(movieId)
        assertThat(favoritesFlow.first()).isEmpty()
    }

    @Test
    fun isFavoriteWhenItemExistInDatabaseReturnsTrue() = runTest {
        val movieId = "movieId"
        dao.addFavorite(movieId)
        assertThat(dao.getFavorites()).isEqualTo(listOf(movieId))

        val result = dao.isFavourite(movieId)
        assertThat(result).isTrue()
    }

    @Test
    fun isFavoriteWhenItemIsNotInDatabaseReturnsFalse() = runTest {
        dao.addFavorite("movieId")
        assertThat(dao.getFavorites()).isEqualTo(listOf("movieId"))

        val result = dao.isFavourite("nonFavoriteMovieId")
        assertThat(result).isFalse()
    }

    @Test
    fun isFavoriteAsFlowWhenItemIsAddedAndThenRemoved() = runTest {
        val movieId = "movieId"
        assertThat(dao.getFavorites()).isEmpty()

        val isFavouriteFlow = dao.isFavouriteAsFlow(movieId)
        assertThat(isFavouriteFlow.first()).isFalse()

        dao.addFavorite(movieId)
        assertThat(isFavouriteFlow.first()).isTrue()

        dao.removeFavorite(movieId)
        assertThat(isFavouriteFlow.first()).isFalse()
    }

}