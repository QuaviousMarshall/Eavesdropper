package com.example.eavesdropper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eavesdropper.presentation.AboutUserCard
import com.example.eavesdropper.ui.theme.EavesdropperTheme

class AboutUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EavesdropperTheme {
                AboutUserCard()
            }
        }
    }
}
