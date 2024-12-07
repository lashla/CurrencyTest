package com.example.myapplication.ui.currencies

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Popup
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Dimens
import com.example.myapplication.ui.theme.RotationValues

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CurrencyDropDown(
    modifier: Modifier,
    selectedCurrency: String,
    onCurrencySelected: (index: Int) -> Unit,
    currenciesList: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .border(
                Dimens.defaultBorderWidth,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(
                    Dimens.spacing1x
                )
            )
    ) {
        val boxWidth = maxWidth
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded } // Toggle dropdown visibility
                .padding(Dimens.spacing1_75x),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = selectedCurrency, style = MaterialTheme.typography.bodySmall)
            Icon(
                painter = painterResource(R.drawable.arrow_down),
                contentDescription = "Expand/Collapse",
                modifier = Modifier
                    .rotate(if (expanded) RotationValues.rotateUpsideDown else RotationValues.rotateReverse)
                    .size(Dimens.spacing3x),
                tint = Color.Unspecified
            )
        }

        if (expanded) {
            val maxPopupHeight = Dimens.spacing6x * 6
            Popup(
                alignment = Alignment.TopStart,
                onDismissRequest = { expanded = false } // Dismiss dropdown when clicked outside
            ) {
                Box(
                    modifier = Modifier
                        .width(boxWidth)
                        .background(Color.White)
                        .border(
                            Dimens.defaultBorderWidth,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(Dimens.spacing1x)
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = maxPopupHeight) // Restrict maximum height for scrolling
                    ) {
                        items(currenciesList.count()) { index ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onCurrencySelected(index) // Update selected currency
                                        expanded = false // Collapse dropdown
                                    }
                                    .background(
                                        if (selectedCurrency == currenciesList[index]) MaterialTheme.colorScheme.surfaceContainerLow
                                        else Color.Transparent
                                    )
                                    .padding(Dimens.spacing1_75x),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = currenciesList[index], style = MaterialTheme.typography.bodySmall)
                                if (index == 0) {
                                    Icon(
                                        painter = painterResource(R.drawable.arrow_down),
                                        contentDescription = "Expand/Collapse",
                                        modifier = Modifier
                                            .rotate(
                                                if (expanded) RotationValues.rotateUpsideDown
                                                else RotationValues.rotateReverse
                                            )
                                            .size(Dimens.spacing3x),
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}