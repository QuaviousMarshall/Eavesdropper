package com.example.eavesdropper.data.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eavesdropper.presentation.auth.AuthState
import com.example.eavesdropper.data.navigation.AuthNavGraph
import com.example.eavesdropper.presentation.screens.main.MainScreen
import com.example.eavesdropper.presentation.viewmodels.AuthViewModel

@Composable
fun AppRoot() {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.state.collectAsStateWithLifecycle()


    when (authState) {
        AuthState.Loading -> SplashScreen()

        AuthState.Unauthorized -> AuthNavGraph(authViewModel)

        AuthState.Authorized -> MainScreen(authViewModel)

        is AuthState.Error -> AuthNavGraph(authViewModel)
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

