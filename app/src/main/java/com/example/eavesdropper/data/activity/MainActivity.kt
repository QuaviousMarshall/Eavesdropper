package com.example.eavesdropper.data.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eavesdropper.data.app.AppRoot
import com.example.eavesdropper.presentation.ui.theme.EavesdropperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EavesdropperTheme {
                AppRoot()
            }
        }
    }
}