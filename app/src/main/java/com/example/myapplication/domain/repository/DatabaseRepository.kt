package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.CurrenciesEntity

interface DatabaseRepository {
    suspend fun saveItemToDatabase(baseCurrency: String, exchangeCurrency: String, rate: Double)
    suspend fun getFavourites(): List<CurrenciesEntity>
    suspend fun deleteFromFavourites(baseCurrency: String, exchangeCurrency: String)
}