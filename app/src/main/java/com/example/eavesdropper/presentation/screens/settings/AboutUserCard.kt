package com.example.eavesdropper.presentation.screens.settings

import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.eavesdropper.R
import com.example.eavesdropper.presentation.viewmodels.AuthViewModel
import com.example.eavesdropper.presentation.ui.theme.Aqua
import com.example.eavesdropper.presentation.ui.theme.DeepSkyBlue
import com.example.eavesdropper.presentation.viewmodels.ListOfAsksViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUserCard(
    paddingValues: PaddingValues,
    viewModel: AuthViewModel
) {
    val userInfo = remember {
        viewModel.getUserInfo()
    }

    val viewModel: ListOfAsksViewModel = hiltViewModel()
    val asks by viewModel.asks.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {

        ProfileInfoRow {
            ProfileInfoText(R.string.about_user_login, modifier = Modifier.weight(1f))
            Text(
                text = userInfo?.email ?: "-",
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        ProfileInfoRow {
            ProfileInfoText(R.string.count_of_asks, modifier = Modifier.weight(1f))
            Text(
                text = "${asks.size}",
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        ProfileInfoRow {
            ProfileInfoText(R.string.account_creating_date, modifier = Modifier.weight(1f))
            Text(
                text = userInfo?.createdAt?.let { formatDate(it) } ?: "-",
                modifier = Modifier.padding(8.dp),
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

fun formatDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}


@Composable
fun ProfileInfoRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(aboutUserGetColor())
    ) {
        content()
    }
}

@Composable
fun ProfileInfoText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.padding(8.dp),
        text = stringResource(textRes),
        fontFamily = FontFamily.Serif,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    )
}

@Composable
fun aboutUserGetColor(): Color {
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





