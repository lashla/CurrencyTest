package com.example.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class ConcurrencyModule {

    /**
     * Creates a coroutines Dispatcher that runs tasks in Android UI thread.
     */
    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    /**
     * Creates a coroutines Dispatcher that runs tasks in Android main thread.
     */
    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    /**
     * Creates an coroutines Dispatcher that runs tasks a background thread pool.
     */
    @Provides
    @BackgroundDispatcher
    fun provideBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

/**
 * This is intended for computational work.
 * This can be used for event-loops, processing callbacks and other computational work.
 * Do not perform IO-bound work on this scheduler.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher

/**
 * This can be used for asynchronously performing blocking IO.
 * Do not perform computational work on this scheduler.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class BackgroundDispatcher

/**
 * This can be used for changing the UI .
 * Do not perform computational and IO-bound work on this scheduler.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher