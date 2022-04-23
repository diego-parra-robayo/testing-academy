package com.wizeline.academy.testing.test_utils.di

import com.wizeline.academy.testing.data.network.MoviesApi
import com.wizeline.academy.testing.di.NetworkModule
import com.wizeline.academy.testing.test_utils.FakeMoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object NetworkTestModule {
    @Singleton
    @Provides
    fun providesMoviesApi(): MoviesApi = FakeMoviesApi()
}
