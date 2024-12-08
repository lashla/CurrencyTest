package com.example.myapplication.ui.currencies

import com.example.myapplication.ui.models.CurrenciesUiModel

data class CurrenciesScreenState(
    val currenciesList: MutableList<String> = mutableListOf<String>(),
    val selectedCurrency: String = "USD",
    val currenciesRatesMap: List<CurrenciesUiModel> = listOf(),
    val isFavourite: Boolean = false,
    val isDropDownExpanded: Boolean = false,
)
