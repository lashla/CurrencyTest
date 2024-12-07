package com.example.myapplication.data.network

import com.example.myapplication.data.model.LatestRatesResponse
import com.example.myapplication.data.model.SymbolsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("exchangerates_data/symbols")
    fun getSymbols(): Call<SymbolsResponse>

    @GET("exchangerates_data/latest")
    fun getLatestRates(
        @Query("base") base: String
    ): Call<LatestRatesResponse>
}