package com.example.eavesdropper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eavesdropper.ui.theme.EavesdropperTheme
import com.example.eavesdropper.presentation.LogInCard
import com.example.eavesdropper.presentation.MainCard

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EavesdropperTheme {
                MainCard()
            }
        }
    }
}