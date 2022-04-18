package com.wizeline.academy.testing.di

import android.content.Context
import androidx.room.Room
import com.wizeline.academy.testing.data.local.FavoritesDao
import com.wizeline.academy.testing.data.local.TestingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesRoomDb(@ApplicationContext appContext: Context): TestingDatabase {
        return Room
            .databaseBuilder(appContext, TestingDatabase::class.java, TestingDatabase.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providesFavoritesDao(testingDatabase: TestingDatabase): FavoritesDao {
        return testingDatabase.favoritesDao()
    }

}