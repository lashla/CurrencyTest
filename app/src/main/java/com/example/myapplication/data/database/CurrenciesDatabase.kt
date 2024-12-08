package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.CurrenciesEntity

@Database(entities = [CurrenciesEntity::class], version = 1, exportSchema = false)
abstract class CurrenciesDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao

    companion object {
        const val DATABASE_NAME = "currencies_database"
    }
}
