package com.example.myapplication.data.repository

import com.example.myapplication.data.database.CurrenciesDao
import com.example.myapplication.data.model.CurrenciesEntity
import com.example.myapplication.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val currenciesDao: CurrenciesDao): DatabaseRepository {
    override suspend fun saveItemToDatabase(
        baseCurrency: String,
        exchangeCurrency: String,
        rate: Double
    ) {
        val rateEntity = CurrenciesEntity(
            base = baseCurrency,
            currencyCode = exchangeCurrency,
            rate = rate
        )
        currenciesDao.insertRates(rateEntity)
    }

    override suspend fun getFavourites(): List<CurrenciesEntity> {
        return currenciesDao.getAllCurrencies()
    }

    override suspend fun deleteFromFavourites(
        baseCurrency: String,
        exchangeCurrency: String
    ) {
        return currenciesDao.deleteRate(baseCurrency, exchangeCurrency)
    }
}