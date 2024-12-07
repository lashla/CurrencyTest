package com.example.myapplication.ui.currencies

data class CurrenciesScreenState(
    val currenciesList: MutableList<String> = mutableListOf<String>(),
    val selectedCurrency: String = "USD",
    val currenciesRatesMap: List<Pair<String, Double>> = listOf(),
    val isFavourite: Boolean = false,
    val isDropDownExpanded: Boolean = false,
)
