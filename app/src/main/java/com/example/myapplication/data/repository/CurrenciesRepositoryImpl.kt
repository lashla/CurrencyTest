package com.example.myapplication.data.repository

import com.example.myapplication.data.model.LatestRatesResponse
import com.example.myapplication.data.model.SymbolsResponse
import com.example.myapplication.data.network.CurrencyApiService
import com.example.myapplication.domain.repository.CurrenciesRepository
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    private val currencyApiService: CurrencyApiService
) :
    CurrenciesRepository {

    override suspend fun fetchCurrencies(): SymbolsResponse? {
        val symbolsResponse = currencyApiService.getSymbols().execute()
        return if (symbolsResponse.isSuccessful) {
            symbolsResponse.body()
        } else {
            null
        }
    }

    override suspend fun fetchCurrencyRates(base: String): LatestRatesResponse? {
        val currencyRatesResponse = currencyApiService.getLatestRates(base = base).execute()
        return if (currencyRatesResponse.isSuccessful) {
            currencyRatesResponse.body()
        } else {
            null
        }
    }
}