package com.example.myapplication.ui.favourites

import com.example.myapplication.ui.models.FavouritesUiModel

data class FavouritesScreenState(
    val savedItemsList: List<FavouritesUiModel> = listOf()
)
