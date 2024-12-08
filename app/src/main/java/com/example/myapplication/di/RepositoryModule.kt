package com.example.myapplication.di

import com.example.myapplication.data.repository.CurrenciesRepositoryImpl
import com.example.myapplication.data.repository.DatabaseRepositoryImpl
import com.example.myapplication.domain.repository.CurrenciesRepository
import com.example.myapplication.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(repositoryImpl: CurrenciesRepositoryImpl): CurrenciesRepository =
        repositoryImpl

    @Singleton
    @Provides
    fun provideDataBaseRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository =
        databaseRepositoryImpl
}