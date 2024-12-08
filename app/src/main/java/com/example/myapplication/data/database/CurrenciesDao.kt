package com.example.myapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.model.CurrenciesEntity

@Dao
interface CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(currencyRatesEntity: CurrenciesEntity)

    @Query("SELECT * FROM currency_rates")
    suspend fun getAllCurrencies(): List<CurrenciesEntity>

    @Query("DELETE FROM currency_rates WHERE base = :base AND currencyCode = :currencyCode")
    suspend fun deleteRate(base: String, currencyCode: String)
}