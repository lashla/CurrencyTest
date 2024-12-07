package com.example.myapplication.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Dimens
import com.example.myapplication.ui.theme.navigationItemsColors

@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int,
    navController: NavHostController,
    onSelectedItemChanged: (Int) -> Unit
) {
    Surface(shadowElevation = Dimens.spacing1_25x) {
        NavigationBar(
            modifier = Modifier,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            topLevelRoutes.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        onSelectedItemChanged(index)
                        navController.navigate(item.route)
                    },
                    colors = navigationItemsColors(),
                    label = {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            painter = painterResource(
                                if (index == selectedItemIndex) {
                                    item.selectedIconRes
                                } else item.unSelectedIconRes
                            ),
                            contentDescription = item.name
                        )
                    }
                )
            }
        }
    }
}

val topLevelRoutes = listOf(
    TopLevelRoute("Profile", Currencies, R.drawable.currencies_icon, R.drawable.currencies_icon),
    TopLevelRoute("Friends", Favourites, R.drawable.favorites_icon, R.drawable.favorites_icon)
)