package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.LatestRatesResponse
import com.example.myapplication.data.model.SymbolsResponse

interface CurrenciesRepository {
    suspend fun fetchCurrencies(): SymbolsResponse?
    suspend fun fetchCurrencyRates(base: String): LatestRatesResponse?
}