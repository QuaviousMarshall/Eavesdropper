package com.example.eavesdropper.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eavesdropper.data.authorization.AuthState
import com.example.eavesdropper.presentation.LogInCard
import com.example.eavesdropper.presentation.RegistrationCard
import com.example.eavesdropper.presentation.viewmodels.AuthViewModel

@Composable
fun AuthNavGraph(viewModel: AuthViewModel) {
    val navController = rememberNavController()
    val authState by viewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.LogInScreen.route
    ) {
        composable(Screen.LogInScreen.route) {
            LogInCard(
                onLoginClick = viewModel::signIn,
                onRegisterClick = {
                    navController.navigate(Screen.RegistrationScreen.route)
                },
                isLoading = authState is AuthState.Loading
            )
        }

        composable(Screen.RegistrationScreen.route) {
            RegistrationCard(
                onRegisterClick = viewModel::signUp,
                onLoginClick = {
                    navController.popBackStack()
                },
                isLoading = authState is AuthState.Loading,
            )
        }
    }
}
