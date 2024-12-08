package com.example.myapplication.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@Composable
fun FilterRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(Dimens.spacing3x)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    ) {
        if (selected) {
            Icon(
                painter = painterResource(R.drawable.radio_button_active),
                contentDescription = "Enabled radio button",
                tint = Color.Unspecified
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.radio_button_inactive),
                contentDescription = "Disabled radio button",
                tint = Color.Unspecified
            )
        }
    }
}
