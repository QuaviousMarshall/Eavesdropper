package com.example.eavesdropper.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eavesdropper.domain.entity.NavigationItem
import com.example.eavesdropper.navigation.AppNavGraph
import com.example.eavesdropper.navigation.Screen
import com.example.eavesdropper.navigation.rememberNavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            when (navBackStackEntry?.destination?.route) {
                Screen.RegistrationScreen.route -> {}

                Screen.LogInScreen.route -> {}

                else -> BottomBar(
                    navigationState,
                    currentRoute,
                    navBackStackEntry
                )
            }
        },
        topBar = {
            when (navBackStackEntry?.destination?.route) {
                Screen.ListOfAsksScreen.route -> ListTopBar {
                    navigationState.navigateTo(Screen.SettingsScreen.route)
                }

                Screen.SettingsScreen.route -> SettingsTopBar {
                    navigationState.navHostController.popBackStack()
                }

                Screen.AboutAppScreen.route -> SettingsTopBar {
                    navigationState.navHostController.popBackStack()
                }

                Screen.AboutAccountScreen.route -> SettingsTopBar {
                    navigationState.navHostController.popBackStack()
                }
            }
        }
    ) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            mainScreenContent = { MainCard(it) },
            listOfAsksScreenContent = { ListOfAsksCard(it) },
            aboutAppScreenContent = {
                AboutAppCard(it)
            },
            settingsScreenContent = {
                SettingsCard(
                    paddingValues = it,
                    onProfileButtonClick = {
                        navigationState.navigateTo(Screen.AboutAccountScreen.route)
                    },
                    onLogOutButtonClick = {
                        navigationState.navigateTo(Screen.LogInScreen.route)
                    },
                    onAppInfoButtonClick = {
                        navigationState.navigateTo(Screen.AboutAppScreen.route)
                    },
                )
            },
            aboutUserScreenContent = {
                AboutUserCard(it)
            },
            logInScreenContent = {
                LogInCard {
                    navigationState.navigateToRoot(Screen.RegistrationScreen.route)
                }
            },
            registrationScreenContent = {
                RegistrationCard {
                    navigationState.navigateToRoot(Screen.LogInScreen.route)
                }
            }
        )
    }
}