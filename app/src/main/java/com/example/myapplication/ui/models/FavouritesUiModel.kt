package com.example.myapplication.ui.models

import com.example.myapplication.data.model.CurrenciesEntity

data class FavouritesUiModel(
    val base: String = "",
    val title: String = "",
    val value: Double = 0.0,
    val isFavourite: Boolean = true
)

fun List<CurrenciesEntity>.toUiModel(): MutableList<FavouritesUiModel> {
    val uiModelList = mutableListOf<FavouritesUiModel>()
    this.forEach {
        uiModelList.add(FavouritesUiModel(
            it.base,
            it.currencyCode,
            it.rate
        ))
    }
    return uiModelList
}