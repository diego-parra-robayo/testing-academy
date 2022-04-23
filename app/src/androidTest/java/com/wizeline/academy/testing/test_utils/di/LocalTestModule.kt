package com.wizeline.academy.testing.test_utils.di

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wizeline.academy.testing.data.local.FavoritesDao
import com.wizeline.academy.testing.data.local.TestingDatabase
import com.wizeline.academy.testing.di.LocalModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocalModule::class]
)
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
