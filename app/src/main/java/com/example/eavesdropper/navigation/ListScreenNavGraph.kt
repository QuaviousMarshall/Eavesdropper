package com.example.eavesdropper.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.listScreenNavGraph(
    listOfAsksScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    aboutAppScreenContent: @Composable () -> Unit,
    aboutUserScreenContent: @Composable () -> Unit,
    logInScreenContent: @Composable () -> Unit,
    registrationScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.ListOfAsksScreen.route,
        route = Screen.List.route
    ) {
        composable(Screen.SettingsScreen.route) {
            settingsScreenContent()
        }
        composable(Screen.AboutAppScreen.route) {
            aboutAppScreenContent()
        }
        composable(Screen.AboutAccountScreen.route) {
            aboutUserScreenContent()
        }
        composable(Screen.LogInScreen.route) {
            logInScreenContent()
        }
        composable(Screen.ListOfAsksScreen.route) {
            listOfAsksScreenContent()
        }
        composable(Screen.RegistrationScreen.route) {
            registrationScreenContent()
        }
    }
}