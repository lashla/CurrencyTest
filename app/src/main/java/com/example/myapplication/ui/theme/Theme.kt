package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    Box(modifier = Modifier) {
        CompositionLocalProvider {
            MaterialTheme(
                colorScheme = lightColorScheme,
                typography = typography(),
                content = content
            )
        }
    }
}

private const val BLUE = 0xFF009FDB
private const val PRIMARY = 0xFF0A2FA7
private const val WHITE = 0xFFFFFFFF
private const val WHITE_TRANSPARENT = 0x80FFFFFF
private const val BLACK_TRANSPARENT = 0x08000000
private const val ON_PRIMARY_COLOR = 0xFF343138
private const val PRIMARY_COLOR_4_60 = 0xFFEBEBEB
private const val PRIMARY_COLOR_4_70 = 0xFF616F74
private const val PRIMARY_CONTAINER = 0xFFF0F2F8
private const val SECONDARY_COLOR = 0xFF9DACDC
private const val OTHER_COLOR_GREY_6 = 0xFF788488
private const val DIVIDER_COLOR = 0xFFDBDEDF
private const val LIGHT_RED_COLOR = 0xFFFDD9D5
private const val LIGHT_RED_COLOR_2 = 0xFFF9E5EA
private const val DARK_RED_COLOR = 0xFFC70032
private const val HEADER_CONTAINER = 0xFFF6F6F6
private const val BLUE_COLOR_10 = 0xFFE5F5FB
private const val SHADOW_COLOR = 0x3F537C0F
private const val LIGHT_BLUE_BACKGROUND = 0xFFDEE4F8

private val lightColorScheme = lightColorScheme(
    primary = Color(PRIMARY),
    onPrimary = Color(WHITE),
    primaryContainer = Color(PRIMARY_CONTAINER),
    onPrimaryContainer = Color(ON_PRIMARY_COLOR),
    secondary = Color(SECONDARY_COLOR),
    onSecondary = Color(WHITE),
    secondaryContainer = Color(WHITE_TRANSPARENT),
    onSecondaryContainer = Color(PRIMARY),
    background = Color(WHITE),
    onBackground = Color(ON_PRIMARY_COLOR),
    surface = Color(WHITE),
    onSurface = Color(ON_PRIMARY_COLOR),
    onSurfaceVariant = Color(PRIMARY_COLOR_4_60),
    outlineVariant = Color(DIVIDER_COLOR),
    error = Color(DARK_RED_COLOR),
    onError = Color(WHITE),
    errorContainer = Color(LIGHT_RED_COLOR),
    onErrorContainer = Color(ON_PRIMARY_COLOR),
    outline = Color(OTHER_COLOR_GREY_6),
    tertiaryContainer = Color(HEADER_CONTAINER),
    inverseSurface = Color(BLUE_COLOR_10),
    inverseOnSurface = Color(ON_PRIMARY_COLOR),
    surfaceContainer = Color(BLACK_TRANSPARENT),
    surfaceContainerLowest = Color(SHADOW_COLOR),
    tertiary = Color(BLUE),
    surfaceContainerLow = Color(LIGHT_BLUE_BACKGROUND),
    surfaceContainerHigh = Color(LIGHT_RED_COLOR_2),
    onTertiary = Color(WHITE),
    onTertiaryContainer = Color(PRIMARY_COLOR_4_70)
)

@Composable
fun navigationItemsColors() =
    NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
        indicatorColor = MaterialTheme.colorScheme.surfaceContainerLow,
        unselectedIconColor = MaterialTheme.colorScheme.secondary,
        unselectedTextColor = MaterialTheme.colorScheme.secondary,
        disabledIconColor = MaterialTheme.colorScheme.secondary,
        disabledTextColor = MaterialTheme.colorScheme.secondary,
    )

@Composable
private fun typography(): Typography {
    return MaterialTheme.typography.copy(
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 36.sp
        ),

        headlineMedium = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),

        headlineSmall = MaterialTheme.typography.headlineSmall.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ),

        displayLarge = MaterialTheme.typography.displayLarge.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 70.sp,
            lineHeight = 70.sp
        ),

        displayMedium = MaterialTheme.typography.displayMedium.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ),

        bodyLarge = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),

        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        ),

        bodySmall = MaterialTheme.typography.bodySmall.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),

        titleSmall = MaterialTheme.typography.titleSmall.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),

        labelLarge = MaterialTheme.typography.labelLarge.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),

        labelMedium = MaterialTheme.typography.labelMedium.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),

        titleMedium = MaterialTheme.typography.titleMedium.copy(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
    )
}

private val interFontFamily = FontFamily(
    Font(resId = R.font.inter_medium, FontWeight.Medium),
    Font(resId = R.font.inter_semi_bold, FontWeight.SemiBold),
    Font(resId = R.font.inter_bold, FontWeight.Bold)
)
