package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.database.CurrenciesDatabase
import com.example.myapplication.data.database.CurrenciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): CurrenciesDatabase {
        return Room.databaseBuilder(
            context,
            CurrenciesDatabase::class.java,
            CurrenciesDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrenciesDao(currenciesDatabase: CurrenciesDatabase): CurrenciesDao {
        return currenciesDatabase.currenciesDao()
    }
}