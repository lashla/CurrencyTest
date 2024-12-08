package com.example.myapplication.ui.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.ui.CurrenciesViewModel
import com.example.myapplication.ui.theme.CurrencyItem
import com.example.myapplication.ui.theme.Dimens

@Composable
fun FavouritesScreen(viewModel: CurrenciesViewModel) {
    val uiState by viewModel.favouritesUiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.fetchFavourites()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    FavouritesScreenContent(
        uiState = uiState, onFavouriteClicked = { index, isFavourite ->
            viewModel.updateFavouriteState(index, isFavourite, uiState.savedItemsList)
        }
    )
}

@Composable
private fun FavouritesScreenContent(
    uiState: FavouritesScreenState,
    onFavouriteClicked: (Int, Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        FavouritesTopAppBar()
        Spacer(modifier = Modifier.height(Dimens.spacing2x))
        FavouritesList(uiState, onFavouriteClicked = onFavouriteClicked)
    }
}

@Composable
private fun FavouritesList(
    uiState: FavouritesScreenState,
    onFavouriteClicked: (Int, Boolean) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = Dimens.spacing2x)) {
        itemsIndexed(uiState.savedItemsList) { cardItemIndex, cardItem ->
            val title = buildString {
                append(cardItem.base)
                append(DIVIDER)
                append(cardItem.title)
            }

            CurrencyItem(
                itemTitle = title,
                itemValue = cardItem.value,
                onFavouriteClicked = { onFavouriteClicked(cardItemIndex, it) },
                isChecked = cardItem.isFavourite
            )
            if (cardItemIndex < uiState.savedItemsList.size - 1) {
                Spacer(modifier = Modifier.height(Dimens.spacing1x))
            }
        }
    }
}

@Composable
private fun FavouritesTopAppBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = Dimens.spacing1_25x,
                        horizontal = Dimens.spacing2x
                    ),
                text = stringResource(R.string.filters),
                style = MaterialTheme.typography.headlineMedium
            )
            HorizontalDivider(
                thickness = Dimens.defaultBorderWidth,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private const val DIVIDER = "/"