package com.example.eavesdropper.presentation.screens.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.eavesdropper.R
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.presentation.ui.theme.myColor
import com.example.eavesdropper.presentation.viewmodels.MainViewModel
import com.example.eavesdropper.service.VoiceRecognitionService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCard(
    paddingValues: PaddingValues,
    viewModel: MainViewModel = hiltViewModel()
) {

    val last3Asks by viewModel.last3Asks.collectAsState()
    val isTronEnabled by viewModel.isTronEnabled.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {

        AskMainIcon(isActive = isTronEnabled)

        Last3AsksList(last3Asks)

        TronToggleButton(
            isEnabled = isTronEnabled,
            viewModel = viewModel
        )

        Spacer(Modifier.height(32.dp))
    }
}


@Composable
fun Last3AsksList(
    last3AsksList: List<Ask>
) {

    if (last3AsksList.isEmpty()) return

    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(myColor()),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.last_3_asks),
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(Modifier.height(16.dp))

            last3AsksList.forEach { ask ->
                AskRow(ask)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AskMainIcon(isActive: Boolean) {
    val transition = rememberInfiniteTransition(label = "tron")
    val scale by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = if (isActive) 1.1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val tint = if (isActive) myColor() else Color.Gray

    Box(
        modifier = Modifier.graphicsLayer(
            scaleX = scale,
            scaleY = scale
        )
    ) {
        Image(
            painter = painterResource(R.drawable.question_mark_circle_svg_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(tint)
        )
    }
}

@Composable
fun rememberAudioPermissionLauncher(
    onDenied: () -> Unit
): ActivityResultLauncher<String> {

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            onDenied()
        }
    }
}


@Composable
fun TronToggleButton(
    isEnabled: Boolean,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val micPermissionText = stringResource(R.string.mic_permission_required)

    val permissionLauncher = rememberAudioPermissionLauncher(
        onDenied = {
            Toast
                .makeText(
                    context,
                    micPermissionText,
                    Toast.LENGTH_LONG
                )
                .show()
        }
    )

    val containerColor =
        when {
            isEnabled -> myColor()
            else -> Color.Gray
        }

    val text =
        when {
            isEnabled -> stringResource(R.string.turn_off_thron)
            else -> stringResource(R.string.turn_on_thron)
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ElevatedButton(
            onClick = {

                if (!hasAudioPermission(context)) {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    return@ElevatedButton
                }

                val intent = Intent(context, VoiceRecognitionService::class.java)

                if (isEnabled) {
                    context.stopService(intent)
                } else {
                    ContextCompat.startForegroundService(context, intent)
                }

                viewModel.onTronButtonClick()
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = containerColor,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = text,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun hasAudioPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED
}



@Composable
fun AskRow(ask: Ask) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "${ask.question}?",
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = ask.answer,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

