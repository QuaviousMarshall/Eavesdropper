package com.example.eavesdropper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eavesdropper.ui.theme.EavesdropperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO если неавторизован сетКонтент экран авторизации иначе главный экран???
        setContent {
            EavesdropperTheme {
                MainCard()
            }
        }
    }
}
