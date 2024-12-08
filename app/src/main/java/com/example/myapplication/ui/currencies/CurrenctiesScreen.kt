package com.example.myapplication.ui.currencies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import com.example.myapplication.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.ui.CurrenciesViewModel
import com.example.myapplication.ui.theme.CurrencyDropDown
import com.example.myapplication.ui.theme.CurrencyItem
import com.example.myapplication.ui.theme.Dimens
import com.example.myapplication.ui.theme.Weights

@Composable
fun CurrenciesScreen(viewModel: CurrenciesViewModel, navigateToFilter: () -> Unit) {
    val uiState by viewModel.currenciesUiState.collectAsStateWithLifecycle()
    CurrenciesContent(
        uiState = uiState,
        onCurrencySelected = viewModel::onCurrencySelected,
        navigateToFilter = navigateToFilter,
        onFavouriteClicked = { index, isFavourite ->
            viewModel.updateFavouriteState(index, isFavourite, uiState.currenciesRatesMap)
        }
    )
}

@Composable
private fun CurrenciesContent(
    uiState: CurrenciesScreenState,
    onCurrencySelected: (Int) -> Unit,
    navigateToFilter: () -> Unit,
    onFavouriteClicked: (Int, Boolean) -> Unit
) {
    Column {
        TopAppBar(
            currencyList = uiState.currenciesList,
            onCurrencySelected = onCurrencySelected,
            selectedCurrency = uiState.selectedCurrency,
            navigateToFilter = navigateToFilter
        )
        LazyColumn(modifier = Modifier.padding(horizontal = Dimens.spacing2x)) {
            itemsIndexed(uiState.currenciesRatesMap.toList()) { cardItemIndex, cardItem ->
                CurrencyItem(
                    itemTitle = cardItem.title,
                    itemValue = cardItem.value,
                    isChecked = cardItem.isFavourite,
                    onFavouriteClicked = { onFavouriteClicked(cardItemIndex, it) })
                if (cardItemIndex < uiState.currenciesRatesMap.size - 1) {
                    Spacer(modifier = Modifier.height(Dimens.spacing1x))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    currencyList: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (index: Int) -> Unit,
    navigateToFilter: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.spacing2x)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column {
            Text(
                text = stringResource(R.string.currencies),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(Dimens.spacing1x))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CurrencyDropDown(
                    modifier = Modifier
                        .weight(Weights.WEIGHT1X)
                        .height(Dimens.spacing6x),
                    currenciesList = currencyList,
                    onCurrencySelected = onCurrencySelected,
                    selectedCurrency = selectedCurrency
                )
                Spacer(modifier = Modifier.width(Dimens.spacing1x))
                OutlinedIconButton(
                    modifier = Modifier.size(Dimens.spacing6x),
                    onClick = navigateToFilter,
                    shape = RoundedCornerShape(Dimens.spacing1x),
                    border = BorderStroke(
                        width = Dimens.defaultBorderWidth,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.filter),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.height(Dimens.spacing1x))
            HorizontalDivider(
                thickness = Dimens.defaultBorderWidth,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}