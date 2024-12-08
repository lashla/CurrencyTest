package com.example.myapplication.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.CurrenciesEntity
import com.example.myapplication.di.BackgroundDispatcher
import com.example.myapplication.domain.repository.CurrenciesRepository
import com.example.myapplication.domain.repository.DatabaseRepository
import com.example.myapplication.ui.currencies.CurrenciesScreenState
import com.example.myapplication.ui.favourites.FavouritesScreenState
import com.example.myapplication.ui.filter.FilterOption
import com.example.myapplication.ui.filter.FilterScreenState
import com.example.myapplication.ui.models.CurrenciesUiModel
import com.example.myapplication.ui.models.FavouritesUiModel
import com.example.myapplication.ui.models.mapToUiModel
import com.example.myapplication.ui.models.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository,
    private val sharedPreferences: SharedPreferences,
    private val databaseRepository: DatabaseRepository,
    @BackgroundDispatcher private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _currenciesUiState = MutableStateFlow(CurrenciesScreenState())
    val currenciesUiState: StateFlow<CurrenciesScreenState> = _currenciesUiState

    private val _filterUiState = MutableStateFlow(FilterScreenState())
    val filterUiState: StateFlow<FilterScreenState> = _filterUiState

    private val _favouritesUiState = MutableStateFlow(FavouritesScreenState())
    val favouritesUiState: StateFlow<FavouritesScreenState> = _favouritesUiState

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch(backgroundDispatcher) {
            val currenciesList = currenciesRepository.fetchCurrencies()?.symbols?.keys?.toMutableList() ?: mutableListOf()
            val defaultCurrency = sharedPreferences.getString(DEFAULT_CURRENCY_TAG, DEFAULT_CURRENCY) ?: DEFAULT_CURRENCY

            updateCurrenciesUiState(currenciesList, defaultCurrency)
            fetchCurrencies()
            fetchFavourites()
        }
    }

    private fun updateCurrenciesUiState(currenciesList: MutableList<String>, selectedCurrency: String) {
        _currenciesUiState.update {
            it.copy(
                currenciesList = currenciesList,
                selectedCurrency = selectedCurrency
            )
        }
    }

    private fun fetchCurrencies() {
        viewModelScope.launch(backgroundDispatcher) {
            val currenciesFromDb = databaseRepository.getFavourites()
            val currencyRates = currenciesRepository.fetchCurrencyRates(base = _currenciesUiState.value.selectedCurrency)?.rates
            updateCurrenciesRates(currencyRates, currenciesFromDb)
        }
    }

    private fun updateCurrenciesRates(currencyRates: Map<String, Double>?, currenciesFromDb: List<CurrenciesEntity>) {
        val uiModelList = currencyRates?.mapToUiModel(currenciesFromDb, _currenciesUiState.value.selectedCurrency) ?: mutableListOf()
        _currenciesUiState.update {
            it.copy(currenciesRatesMap = uiModelList)
        }
    }

    fun onCurrencySelected(index: Int) {
        val selectedCurrency = _currenciesUiState.value.currenciesList[index]
        if (selectedCurrency != _currenciesUiState.value.selectedCurrency) {
            updateCurrencySelection(selectedCurrency)
        }
    }

    private fun updateCurrencySelection(selectedCurrency: String) {
        _currenciesUiState.update {
            it.copy(
                currenciesList = it.currenciesList.distinct() as MutableList<String>,
                selectedCurrency = selectedCurrency
            )
        }
        _currenciesUiState.value.currenciesList.add(0, selectedCurrency)
        sharedPreferences.edit().putString(DEFAULT_CURRENCY_TAG, selectedCurrency).apply()
        fetchCurrencies()
    }

    fun onFilterChanged(filterOption: FilterOption) {
        _filterUiState.update { it.copy(selectedFilter = filterOption) }
    }

    fun onApplyFilter() {
        sortCurrencies(_filterUiState.value.selectedFilter)
    }

    private fun sortCurrencies(sortOption: FilterOption) {
        val sortedCurrencies = when (sortOption) {
            FilterOption.QuoteDesc -> _currenciesUiState.value.currenciesRatesMap.sortedByDescending { it.value }
            FilterOption.QuoteAsc -> _currenciesUiState.value.currenciesRatesMap.sortedBy { it.value }
            FilterOption.CodeZA -> _currenciesUiState.value.currenciesRatesMap.sortedByDescending { it.title }
            FilterOption.CodeAZ -> _currenciesUiState.value.currenciesRatesMap.sortedBy { it.title }
            else -> _currenciesUiState.value.currenciesRatesMap
        }
        _currenciesUiState.update {
            it.copy(currenciesRatesMap = sortedCurrencies as MutableList<CurrenciesUiModel>)
        }
    }

    fun fetchFavourites() {
        viewModelScope.launch(backgroundDispatcher) {
            val favourites = databaseRepository.getFavourites().toUiModel()
            _favouritesUiState.update { it.copy(savedItemsList = favourites) }
        }
    }

    @JvmName("updateCurrenciesState")
    fun updateFavouriteState(index: Int, isFavourite: Boolean, currenciesList: List<CurrenciesUiModel>) {
        val updatedList = currenciesList.toMutableList()
        updatedList[index] = updatedList[index].copy(isFavourite = isFavourite)
        _currenciesUiState.update { it.copy(currenciesRatesMap = updatedList) }
        handleFavouriteDatabaseUpdate(updatedList[index], isFavourite)
    }

    @JvmName("updateFavouritesState")
    fun updateFavouriteState(index: Int, isFavourite: Boolean, favouritesList: List<FavouritesUiModel>) {
        val updatedList = favouritesList.toMutableList()
        updatedList[index] = updatedList[index].copy(isFavourite = isFavourite)
        _favouritesUiState.update { it.copy(savedItemsList = updatedList) }
        handleFavouriteDatabaseUpdate(updatedList[index], isFavourite)
    }

    private fun handleFavouriteDatabaseUpdate(item: CurrenciesUiModel, isFavourite: Boolean) {
        viewModelScope.launch(backgroundDispatcher) {
            if (isFavourite) {
                databaseRepository.saveItemToDatabase(_currenciesUiState.value.selectedCurrency, item.title, item.value)
            } else {
                databaseRepository.deleteFromFavourites(_currenciesUiState.value.selectedCurrency, item.title)
            }
        }
    }

    private fun handleFavouriteDatabaseUpdate(item: FavouritesUiModel, isFavourite: Boolean) {
        viewModelScope.launch(backgroundDispatcher) {
            if (isFavourite) {
                databaseRepository.saveItemToDatabase(item.base, item.title, item.value)
            } else {
                databaseRepository.deleteFromFavourites(item.base, item.title)
            }
        }
    }

    companion object {
        private const val DEFAULT_CURRENCY_TAG = "DEFAULT_CURRENCY"
        private const val DEFAULT_CURRENCY = "USD"
    }
}