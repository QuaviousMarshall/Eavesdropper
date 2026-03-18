package com.example.eavesdropper.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eavesdropper.presentation.navigation.AppNavGraph
import com.example.eavesdropper.presentation.navigation.Screen
import com.example.eavesdropper.presentation.navigation.rememberNavigationState
import com.example.eavesdropper.presentation.screens.list.ListOfAsksCard
import com.example.eavesdropper.presentation.screens.settings.AboutAppCard
import com.example.eavesdropper.presentation.screens.settings.AboutUserCard
import com.example.eavesdropper.presentation.screens.settings.SettingsCard
import com.example.eavesdropper.presentation.ui.components.BottomBar
import com.example.eavesdropper.presentation.ui.components.ListTopBar
import com.example.eavesdropper.presentation.ui.components.SettingsTopBar
import com.example.eavesdropper.presentation.viewmodels.AuthViewModel
import com.example.eavesdropper.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(authViewModel: AuthViewModel) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel: MainViewModel = hiltViewModel()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
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
            mainScreenContent = { MainCard(
                paddingValues = it,
                viewModel = viewModel,
            ) },
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
                    },
                    viewModel = viewModel
                )
            },
            aboutUserScreenContent = {
                AboutUserCard(it, authViewModel)
            }

        )
    }
}