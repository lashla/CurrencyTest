package com.example.myapplication.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@Composable
fun CurrencyItem(
    itemTitle: String,
    itemValue: Double,
    isChecked: Boolean,
    onFavouriteClicked: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(Dimens.spacing1_5x)
            )
            .padding(vertical = Dimens.spacing1_5x, horizontal = Dimens.spacing2x)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(itemTitle, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.weight(Weights.WEIGHT1X))
        Text(itemValue.toString(), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.width(Dimens.spacing2x))
        IconToggleButton(
            modifier = Modifier.size(Dimens.spacing2_5x),
            checked = isChecked,
            onCheckedChange = { onFavouriteClicked(it) }) {
            if (isChecked) {
                Icon(
                    modifier = Modifier.size(Dimens.spacing2_5x),
                    painter = painterResource(R.drawable.favorites_icon),
                    contentDescription = "Localized description",
                    tint = Color.Unspecified
                )
            } else {
                Icon(
                    modifier = Modifier.size(Dimens.spacing2_5x),
                    painter = painterResource(R.drawable.favorites_off),
                    contentDescription = "Localized description",
                    tint = Color.Unspecified
                )
            }
        }
    }
}