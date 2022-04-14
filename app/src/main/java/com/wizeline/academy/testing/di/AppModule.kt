package com.wizeline.academy.testing.di

import com.wizeline.academy.testing.data.Mappers
import com.wizeline.academy.testing.data.MappersImpl
import com.wizeline.academy.testing.data.MoviesRepositoryImpl
import com.wizeline.academy.testing.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindsMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    companion object {
        @Provides
        fun providesMappers(): Mappers = MappersImpl
    }

}