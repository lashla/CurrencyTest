package com.example.myapplication.data.model

data class SymbolsResponse(
    val success: Boolean,
    val symbols: Map<String, String>
)

data class LatestRatesResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
)