package com.example.eavesdropper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eavesdropper.presentation.AboutAppCard
import com.example.eavesdropper.ui.theme.EavesdropperTheme

class AboutAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EavesdropperTheme {
                AboutAppCard()
            }
        }
    }
}