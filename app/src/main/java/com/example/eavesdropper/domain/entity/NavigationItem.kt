package com.example.eavesdropper.domain.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.eavesdropper.R

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector
) {

    object Home: NavigationItem(
        titleResId = R.string.navigation_item_home,
        icon = Icons.TwoTone.Home
    )

    object History: NavigationItem(
        titleResId = R.string.navigation_item_history,
        icon = Icons.TwoTone.Favorite
    )

}