package com.example.myapplication.ui.currencies

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.di.BackgroundDispatcher
import com.example.myapplication.domain.repository.CurrenciesRepository
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

    private val _state = MutableStateFlow(CurrenciesScreenState())
    val state: StateFlow<CurrenciesScreenState> = _state

    init {
        viewModelScope.launch(backgroundDispatcher) {
            _state.update {
                it.copy(
                    currenciesList = currenciesRepository.fetchCurrencies()?.symbols?.keys?.toMutableList()
                        ?: mutableListOf(),
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
            _state.update {
                it.copy(
                    currenciesRatesMap = currenciesRepository.fetchCurrencyRates(
                        base = _state.value.selectedCurrency
                    )?.rates?.toList() ?: listOf()
                )
            }
        }
    }

    fun onCurrencySelected(index: Int) {
        if (_state.value.currenciesList.first() != _state.value.currenciesList[index]) {
            _state.update {
                it.copy(
                    currenciesList = it.currenciesList.distinct() as MutableList<String>,
                    selectedCurrency = it.currenciesList[index]
                )
            }
            _state.value.currenciesList.add(0, _state.value.selectedCurrency)
            sharedPreferences.edit()
                .putString(DEFAULT_CURRENCY_TAG, _state.value.currenciesList[index]).apply()
            fetchCurrencies()
        }
    }

    companion object {
        private const val DEFAULT_CURRENCY_TAG = "DEFAULT_CURRENCY"
        private const val DEFAULT_CURRENCY = "USD"
    }
}