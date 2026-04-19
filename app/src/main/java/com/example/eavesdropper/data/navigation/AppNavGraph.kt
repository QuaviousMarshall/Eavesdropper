package com.example.eavesdropper.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    aboutAppScreenContent: @Composable () -> Unit,
    aboutUserScreenContent: @Composable () -> Unit,
    mainScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    listOfAsksScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(Screen.MainScreen.route) {
            mainScreenContent()
        }
        listScreenNavGraph(
            listOfAsksScreenContent = listOfAsksScreenContent,
            aboutAppScreenContent = aboutAppScreenContent,
            aboutUserScreenContent = aboutUserScreenContent,
            settingsScreenContent = settingsScreenContent,
        )
    }
}