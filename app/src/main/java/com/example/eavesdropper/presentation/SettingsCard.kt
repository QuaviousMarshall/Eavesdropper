package com.example.eavesdropper.presentation

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eavesdropper.AboutAppActivity
import com.example.eavesdropper.R
import com.example.eavesdropper.AboutUserActivity
import com.example.eavesdropper.LogOutActivity
import com.example.eavesdropper.ui.theme.Aqua
import com.example.eavesdropper.ui.theme.DeepSkyBlue
import com.example.eavesdropper.domain.entity.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsCard(
    context: Context
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                val selectedItemPosition = remember {
                    mutableIntStateOf(1)
                }
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.History
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.intValue == index,
                        onClick = { selectedItemPosition.intValue = index },
                        icon = { Icon(item.icon, contentDescription = null) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.top_bar_settings),
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            ProfileRow {
                ProfileActionButton(
                    R.string.about_account,
                    {
                        context.startActivity(Intent(context, AboutUserActivity::class.java))
                    }
                )
            }
            ProfileRow {
                ProfileActionButton(
                    R.string.about_app,
                    {
                        context.startActivity(Intent(context, AboutAppActivity::class.java))
                    }
                )
            }
            ProfileRow {
                ProfileActionButton(
                    R.string.log_out_button,
                    {
                        context.startActivity(Intent(context, LogOutActivity::class.java))
                    }
                )
            }
        }
    }

}

@Composable
fun ProfileActionButton(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
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