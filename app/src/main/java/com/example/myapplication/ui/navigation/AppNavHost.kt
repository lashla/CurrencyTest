package com.example.myapplication.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.myapplication.ui.currencies.CurrenciesScreen
import kotlinx.serialization.Serializable

@Serializable
object Currencies

@Serializable
object Favourites

@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPaddingValues: PaddingValues
) {
    val navGraph = remember(navController) {
        navController.createGraph(startDestination = Currencies) {
            composable<Currencies> { CurrenciesScreen() }
        }
    }
    NavHost(
        modifier = Modifier.padding(innerPaddingValues),
        navController = navController,
        graph = navGraph
    )
}