package com.wizeline.academy.testing.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class TestingDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "com.wizeline.academy.testing.db"
    }

    abstract fun favoritesDao(): FavoritesDao
}