package com.example.myapplication.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.myapplication.ui.currencies.CurrenciesScreen
import com.example.myapplication.ui.CurrenciesViewModel
import com.example.myapplication.ui.favourites.FavouritesScreen
import com.example.myapplication.ui.filter.FilterScreen
import kotlinx.serialization.Serializable

@Serializable
object Currencies

@Serializable
object Favourites

@Serializable
object Filters

@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPaddingValues: PaddingValues
) {
    val navGraph = remember(navController) {
        navController.createGraph(startDestination = Currencies) {
            composable<Currencies> {
                val viewModel = hiltViewModel<CurrenciesViewModel>()
                CurrenciesScreen(viewModel) {
                    navController.navigate(Filters)
                }
            }
            composable<Filters> {
                val backStackEntry = navController.getBackStackEntry(Currencies)
                FilterScreen(hiltViewModel(backStackEntry)) {
                    navController.popBackStack()
                }
            }
            composable<Favourites> {
                val backStackEntry = navController.getBackStackEntry(Currencies)
                FavouritesScreen(hiltViewModel(backStackEntry))
            }
        }
    }
    NavHost(
        modifier = Modifier.padding(innerPaddingValues),
        navController = navController,
        graph = navGraph
    )
}
