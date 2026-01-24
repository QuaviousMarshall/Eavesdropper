package com.example.eavesdropper.domain.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.eavesdropper.R
import com.example.eavesdropper.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) {

    object Home: NavigationItem(
        screen = Screen.MainScreen,
        titleResId = R.string.navigation_item_home,
        icon = Icons.TwoTone.Home
    )

    object History: NavigationItem(
        screen = Screen.List,
        titleResId = R.string.navigation_item_history,
        icon = Icons.TwoTone.Favorite
    )

}