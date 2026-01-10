package com.example.eavesdropper.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eavesdropper.R
import com.example.eavesdropper.ui.theme.Aqua
import com.example.eavesdropper.ui.theme.DeepSkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInCard() {

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Row {
                LogInWelcomeText()
            }
            Spacer(Modifier.weight(1f))
            Row {
                LoginPasswordBox()
            }
            Spacer(Modifier.weight(1f))
            Row {
                VersionText()
            }
        }
    }
}

@Composable
fun LoginPasswordBox() {
    Box(
        modifier = Modifier
            .padding(bottom = 1.dp, start = 32.dp, end = 32.dp, top = 1.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.LightGray)
            .background(color = logInGetColor()),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                    text = stringResource(R.string.entrance_in_account),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            var loginText by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = loginText,
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(
                            R.string.login
                        ),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            )
            var passwordText by remember { mutableStateOf(TextFieldValue("")) }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = passwordText,
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(
                            R.string.password
                        ),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButtonLogin {}
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun VersionText() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = stringResource(R.string.version_1_0_1),
            fontSize = 10.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Justify,
            color = DeepSkyBlue
        )
    }
}

@Composable
fun ElevatedButtonLogin(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
    ) {
        ElevatedButton(
            onClick = { onClick() },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = logInGetColorForButton(),
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(
                stringResource((R.string.log_in)),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun LogInWelcomeText() {
    Box(
        modifier = Modifier
            .padding(top = 32.dp, start = 32.dp, end = 32.dp, bottom = 1.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 80.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = DeepSkyBlue,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Text(
                    text = stringResource(id = R.string.yours_hidden_helper),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    color = DeepSkyBlue,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun logInGetColorForButton(): Color {
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

@Composable
fun logInGetColor(): Color {
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