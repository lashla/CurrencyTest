package com.example.myapplication.ui.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.ui.CurrenciesViewModel
import com.example.myapplication.ui.theme.Dimens
import com.example.myapplication.ui.theme.FilterRadioButton
import com.example.myapplication.ui.theme.Weights

@Composable
fun FilterScreen(viewModel: CurrenciesViewModel, navigateBack: () -> Unit) {
    val uiState by viewModel.filterUiState.collectAsStateWithLifecycle()
    FilterContent(
        uiState,
        onFilterSelected = viewModel::onFilterChanged,
        onApplyPressed = viewModel::onApplyFilter,
        navigateBack = navigateBack
    )
}

@Composable
private fun FilterContent(
    uiState: FilterScreenState,
    onFilterSelected: (FilterOption) -> Unit,
    onApplyPressed: () -> Unit,
    navigateBack: () -> Unit
) {
    Column {
        FilterTopAppBar(navigateBack)
        SortOptions(uiState.selectedFilter) {
            onFilterSelected(it)
        }
        Spacer(modifier = Modifier.weight(Weights.WEIGHT1X))
        Button(
            modifier = Modifier
                .padding(Dimens.spacing2x)
                .fillMaxWidth(),
            onClick = {
                onApplyPressed()
                navigateBack()
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.sort_apply),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun FilterTopAppBar(navigateBack: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = Dimens.spacing1_25x,
                        horizontal = Dimens.spacing0_5x
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.size(Dimens.spacing5x),
                    onClick = navigateBack,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "Back button",
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier = Modifier.width(Dimens.spacing0_5x))
                Text(
                    stringResource(R.string.filters),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            HorizontalDivider(
                thickness = Dimens.defaultBorderWidth,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SortOptions(selectedOption: FilterOption, onPotionSelected: (FilterOption) -> Unit) {

    Column(modifier = Modifier.padding(Dimens.spacing2x)) {
        Text(
            text = stringResource(R.string.sort_by),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = Dimens.spacing1_5x)
        )

        SortOption(
            label = stringResource(R.string.sort_code_a_z),
            selected = selectedOption == FilterOption.CodeAZ,
            onClick = { onPotionSelected(FilterOption.CodeAZ) }
        )

        SortOption(
            label = stringResource(R.string.sort_code_z_a),
            selected = selectedOption == FilterOption.CodeZA,
            onClick = { onPotionSelected(FilterOption.CodeZA) }
        )

        SortOption(
            label = stringResource(R.string.sort_quote_asc),
            selected = selectedOption == FilterOption.QuoteAsc,
            onClick = { onPotionSelected(FilterOption.QuoteAsc) }
        )

        SortOption(
            label = stringResource(R.string.sort_quote_desc),
            selected = selectedOption == FilterOption.QuoteDesc,
            onClick = { onPotionSelected(FilterOption.QuoteDesc) }
        )
    }
}

@Composable
private fun SortOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = Dimens.spacing1x),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        FilterRadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}

@Preview
@Composable
private fun FilerHeaderPreview() {
    FilterTopAppBar({})
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun SortOptionPreview() {
    SortOption(label = "A-Z", false) {}
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun SortOptionsPreview() {
    SortOptions(selectedOption = FilterOption.QuoteAsc, {})
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun ScreenContent() {
    FilterContent(uiState = FilterScreenState(FilterOption.QuoteAsc), {}, {}, {})
}