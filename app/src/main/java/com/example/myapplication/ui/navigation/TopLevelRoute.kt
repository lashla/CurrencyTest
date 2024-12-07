package com.example.myapplication.ui.navigation

data class TopLevelRoute<T : Any>(val name: String, val route: T, val unSelectedIconRes: Int, val selectedIconRes: Int)