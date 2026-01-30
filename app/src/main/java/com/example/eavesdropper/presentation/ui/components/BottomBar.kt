package com.example.eavesdropper.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.eavesdropper.domain.entity.NavigationItem
import com.example.eavesdropper.navigation.NavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    navigationState: NavigationState,
    currentRoute: String?,
    navBackStackEntry: NavBackStackEntry?
) {
    NavigationBar(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.History
        )
        items.forEach { item ->

            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navigationState.navigateToRoot(item.screen.route)
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}