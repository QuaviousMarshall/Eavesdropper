package com.example.eavesdropper.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eavesdropper.data.authorization.AuthState
import com.example.eavesdropper.presentation.screens.auth.ForgotPasswordCard
import com.example.eavesdropper.presentation.screens.auth.LogInCard
import com.example.eavesdropper.presentation.screens.auth.RegistrationCard
import com.example.eavesdropper.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthNavGraph(viewModel: AuthViewModel) {
    val navController = rememberNavController()
    val authState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()

                navController.navigate(Screen.LogInScreen.route) {
                    popUpTo(0) { inclusive = true }
                }

                viewModel.resetState()
            }
            else -> Unit
        }
    }

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
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPasswordScreen.route)
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

        composable(Screen.ForgotPasswordScreen.route) {
            ForgotPasswordCard(
                isLoading = authState is AuthState.Loading,
                onSendCodeClick = { email ->
                    scope.launch {
                        viewModel.resetPassword(email)
                            .onSuccess {
                                Toast.makeText(
                                    context,
                                    "Письмо для восстановления пароля отправлено",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.popBackStack(
                                    route = Screen.LogInScreen.route,
                                    inclusive = false
                                )
                            }
                            .onFailure {
                                Toast.makeText(
                                    context,
                                    "Почта введена неверно",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.popBackStack(
                                    route = Screen.LogInScreen.route,
                                    inclusive = false
                                )
                            }
                    }
                }
            )
        }
    }
}
