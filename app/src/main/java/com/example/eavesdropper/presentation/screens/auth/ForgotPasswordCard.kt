package com.example.eavesdropper.presentation.screens.auth

import androidx.compose.foundation.background
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eavesdropper.R
import com.example.eavesdropper.presentation.ui.theme.myColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordCard(
    onSendCodeClick: (String) -> Unit,
    isLoading: Boolean
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            LogInWelcomeText()
            Spacer(Modifier.weight(1f))
            Box(
                onSendCodeClick = onSendCodeClick,
                isLoading = isLoading
            )
            Spacer(Modifier.weight(1f))
            VersionText()
        }
    }
}

@Composable
fun Box(
    onSendCodeClick: (String) -> Unit,
    isLoading: Boolean,
) {
    Box(
        modifier = Modifier
            .padding(bottom = 1.dp, start = 32.dp, end = 32.dp, top = 1.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.onPrimary),
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
                    text = stringResource(R.string.forgot_password),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }

            ForgotPasswordText()

            var loginText by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = loginText,
                onValueChange = { loginText = it },
                enabled = !isLoading,
                label = {
                    Text(
                        text = stringResource(
                            R.string.login_types
                        ),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButtonSendCode(enabled = !isLoading) {
                onSendCodeClick(loginText)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ForgotPasswordText() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp),
            text = stringResource(R.string.type_email),
            fontSize = 12.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Justify,
            color = Color.Black
        )
    }
}

@Composable
fun ElevatedButtonSendCode(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
    ) {
        ElevatedButton(
            enabled = enabled,
            onClick = { onClick() },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = myColor(),
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(
                stringResource((R.string.send_code)),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
