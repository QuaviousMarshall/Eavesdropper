package com.example.eavesdropper.presentation

import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.eavesdropper.R
import com.example.eavesdropper.ui.theme.Aqua
import com.example.eavesdropper.ui.theme.DeepSkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsCard(
    paddingValues: PaddingValues,
    onProfileButtonClick: () -> Unit,
    onLogOutButtonClick: () -> Unit,
    onAppInfoButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        ProfileRow {
            ProfileActionButton(
                R.string.about_account,
                onProfileButtonClick
            )
        }
        ProfileRow {
            ProfileActionButton(
                R.string.about_app,
                onAppInfoButtonClick
            )
        }
        ProfileRow {
            ProfileActionButton(
                R.string.log_out_button,
                onLogOutButtonClick
            )
        }
    }
}

@Composable
fun ProfileActionButton(
    @StringRes textRes: Int,
    onProfileButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onProfileButtonClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = settingsGetColor(),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Black
        )
    ) {
        Text(
            text = stringResource(textRes),
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        content = content
    )
}

@Composable
fun settingsGetColor(): Color {
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        initialValue = Aqua,
        targetValue = DeepSkyBlue,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )
    return color
}