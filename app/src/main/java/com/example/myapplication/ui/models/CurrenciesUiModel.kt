package com.example.myapplication.ui.models

import com.example.myapplication.data.model.CurrenciesEntity

data class CurrenciesUiModel(
    val title: String,
    val value: Double,
    val isFavourite: Boolean = false
)

fun Map<String, Double>.mapToUiModel(
    currenciesEntity: List<CurrenciesEntity>,
    currentBase: String
): MutableList<CurrenciesUiModel> {
    val list = mutableListOf<CurrenciesUiModel>()
    for (value in this) {
        list.add(
            CurrenciesUiModel(
                value.key,
                value.value,
                currenciesEntity.find { it.base == currentBase && it.currencyCode == value.key } != null
            )
        )
    }
    return list
}