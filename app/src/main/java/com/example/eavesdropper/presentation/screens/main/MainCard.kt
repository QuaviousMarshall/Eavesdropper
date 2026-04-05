package com.example.eavesdropper.presentation.screens.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.util.lerp
import androidx.core.content.ContextCompat
import com.example.eavesdropper.R
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.presentation.ui.theme.Black
import com.example.eavesdropper.presentation.ui.theme.DeepSkyBlue
import com.example.eavesdropper.presentation.ui.theme.Turquoise
import com.example.eavesdropper.presentation.viewmodels.MainViewModel
import com.example.eavesdropper.service.VoiceRecognitionService
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCard(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    color: Color
) {
    val n by viewModel.n.collectAsState()
    val lastAsks by viewModel.lastAsks.collectAsState()
    val isTronEnabled by viewModel.isTronEnabled.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 4 })

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {

        AskMainIcon(isActive = isTronEnabled, color = color)

        TronToggleButton(
            isEnabled = isTronEnabled,
            viewModel = viewModel,
            color = color
        )

        Spacer(Modifier.height(16.dp))

        LastAsksList(lastAsks, n, color)

        Spacer(Modifier.height(16.dp))

        VersionChangesCard(pagerState)

    }
}


@Composable
fun LastAsksList(
    lastAsksList: List<Ask>,
    n: Int,
    color: Color
) {

    if (lastAsksList.isEmpty()) return

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            contentColor = color.copy(alpha = 0.6f),
            containerColor = Turquoise
        ),
        modifier = Modifier
            .padding(8.dp),
    ) {
        Column {
            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = String.format(stringResource(R.string.last_3_asks), n),
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(Modifier.height(16.dp))

            lastAsksList.forEach { ask ->
                AskRow(ask, color)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AskMainIcon(isActive: Boolean, color: Color) {
    val transition = rememberInfiniteTransition(label = "tron")
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = if (isActive) 0.7f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val tint = if (isActive) color else Turquoise

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
    viewModel: MainViewModel,
    color: Color
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Switch(
            checked = isEnabled,
            onCheckedChange = {
                if (!hasAudioPermission(context)) {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    return@Switch
                }

                val intent = Intent(context, VoiceRecognitionService::class.java)

                if (isEnabled) {
                    context.stopService(intent)
                } else {
                    ContextCompat.startForegroundService(context, intent)
                }

                viewModel.onTronButtonClick()
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = color,
                checkedTrackColor = color.copy(alpha = 0.5f),
                checkedIconColor = Black,
                uncheckedIconColor = Black,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxSize(),
            thumbContent = {
                Icon(imageVector = Icons.Default.Mic, contentDescription = null)
            }
        )
    }
}

@Composable
fun VersionChangesCard(
    pagerState: PagerState
) {

    val items = listOf(
        "1.0.1" to stringResource(R.string.version_text101),
        "2.0.0" to stringResource(R.string.version_text200),
        "2.1.0" to stringResource(R.string.version_text210),
        "2.2.0" to stringResource(R.string.version_text220)
    )

    HorizontalPager(
        state = pagerState,
        pageSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { page ->

        val item = items[page]

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
            colors = CardDefaults.cardColors(
                containerColor = Turquoise
            )
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
            ) {
                item {
                    Text(
                        text = "Версия ${item.first}",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.second,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.W400,
                        color = Color.Black
                    )
                }
            }
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
fun AskRow(ask: Ask, color: Color) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "${ask.question}?",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.W500,
            color = Color.Black
        )
    }
}

