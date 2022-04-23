package com.wizeline.academy.testing.test_utils.di

import com.wizeline.academy.testing.di.DispatchersModule
import com.wizeline.academy.testing.di.IoDispatcher
import com.wizeline.academy.testing.di.IoScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

//  This is not actually necessary but it is just to demonstrate how to replace a hilt module
//  for testing purpose

/*
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatchersModule::class]
)
@ExperimentalCoroutinesApi
object FakeDispatchersModule {

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = UnconfinedTestDispatcher()

    @Provides
    @IoScheduler
    fun providesIoScheduler(): Scheduler = Schedulers.trampoline()
}
*/
