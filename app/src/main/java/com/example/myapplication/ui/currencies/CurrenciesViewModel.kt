package com.example.myapplication.ui.currencies

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.di.BackgroundDispatcher
import com.example.myapplication.domain.repository.CurrenciesRepository
import com.example.myapplication.ui.filter.FilterOption
import com.example.myapplication.ui.filter.FilterScreenState
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
    @BackgroundDispatcher private val backgroundDispatcher: CoroutineDispatcher,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _currenciesUiState = MutableStateFlow(CurrenciesScreenState())
    val currenciesUiState: StateFlow<CurrenciesScreenState> = _currenciesUiState

    private val _filterUiState = MutableStateFlow(FilterScreenState())
    val filterUiState: StateFlow<FilterScreenState> = _filterUiState

    init {
        viewModelScope.launch(backgroundDispatcher) {
            val currenciesList =
                currenciesRepository.fetchCurrencies()?.symbols?.keys?.toMutableList()
                    ?: mutableListOf()
            _currenciesUiState.update {
                it.copy(
                    currenciesList = currenciesList,
                    selectedCurrency = sharedPreferences.getString(
                        DEFAULT_CURRENCY_TAG,
                        DEFAULT_CURRENCY
                    ) ?: DEFAULT_CURRENCY
                )
            }
            fetchCurrencies()
        }
    }

    private fun fetchCurrencies() {
        viewModelScope.launch(backgroundDispatcher) {
            val currencyRates = currenciesRepository.fetchCurrencyRates(
                base = _currenciesUiState.value.selectedCurrency
            )?.rates?.toList() ?: listOf()
            _currenciesUiState.update {
                it.copy(
                    currenciesRatesMap = currencyRates
                )
            }
        }
    }

    fun onCurrencySelected(index: Int) {
        if (_currenciesUiState.value.currenciesList.first() != _currenciesUiState.value.currenciesList[index]) {
            _currenciesUiState.update {
                it.copy(
                    currenciesList = it.currenciesList.distinct() as MutableList<String>,
                    selectedCurrency = it.currenciesList[index]
                )
            }
            _currenciesUiState.value.currenciesList.add(
                0,
                _currenciesUiState.value.selectedCurrency
            )
            sharedPreferences.edit()
                .putString(DEFAULT_CURRENCY_TAG, _currenciesUiState.value.currenciesList[index])
                .apply()
            fetchCurrencies()
        }
    }

    fun onFilterChanged(filterOption: FilterOption) {
        _filterUiState.update {
            it.copy(selectedFilter = filterOption)
        }
    }

    fun onApplyFilter() {
        sortCurrencies(_filterUiState.value.selectedFilter)
    }

    private fun sortCurrencies(
        sortOption: FilterOption
    ) {
        val currencies = _currenciesUiState.value.currenciesRatesMap
        _currenciesUiState.update {
            it.copy(currenciesRatesMap = when (sortOption) {
                FilterOption.QuoteDesc -> currencies.toList().sortedByDescending { it.second }
                FilterOption.QuoteAsc -> currencies.toList().sortedBy { it.second }
                FilterOption.CodeZA -> currencies.toList().sortedByDescending { it.first }
                FilterOption.CodeAZ -> currencies.toList().sortedBy { it.first }
                else -> currencies.toList() // Default: no sorting
            }
            )
        }
    }

    companion object {
        private const val DEFAULT_CURRENCY_TAG = "DEFAULT_CURRENCY"
        private const val DEFAULT_CURRENCY = "USD"
    }
}