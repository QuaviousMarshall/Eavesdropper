package com.example.eavesdropper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eavesdropper.ui.theme.AboutAppCard
import com.example.eavesdropper.ui.theme.EavesdropperTheme
import com.example.eavesdropper.ui.theme.LoginCard
import com.example.eavesdropper.ui.theme.MainCard
import com.example.eavesdropper.ui.theme.SettingsCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EavesdropperTheme {
                AboutAppCard()
            }
        }
    }
}
