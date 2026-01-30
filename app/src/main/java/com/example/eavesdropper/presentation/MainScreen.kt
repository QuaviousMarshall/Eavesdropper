package com.example.eavesdropper.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eavesdropper.navigation.AppNavGraph
import com.example.eavesdropper.navigation.Screen
import com.example.eavesdropper.navigation.rememberNavigationState
import com.example.eavesdropper.presentation.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(authViewModel: AuthViewModel) {
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
                    onAppInfoButtonClick = {
                        navigationState.navigateTo(Screen.AboutAppScreen.route)
                    },
                    onLogoutClick = {
                        authViewModel.logout()
                    }
                )
            },
            aboutUserScreenContent = {
                AboutUserCard(it, authViewModel)
            }

        )
    }
}