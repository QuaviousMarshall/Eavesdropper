package com.example.eavesdropper.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eavesdropper.R
import com.example.eavesdropper.presentation.ui.theme.Black
import com.example.eavesdropper.presentation.ui.theme.DeepSkyBlue
import com.example.eavesdropper.presentation.ui.theme.myColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInCard(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
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
            LoginPasswordBox(
                onLoginClick = onLoginClick,
                onForgotPasswordClick = onForgotPasswordClick,
                isLoading = isLoading
            )
            Spacer(Modifier.weight(1f))
            DontHaveAccountYet {
                onRegisterClick()
            }
            Spacer(Modifier.weight(1f))
            VersionText()
        }
    }
}

@Composable
fun LoginPasswordBox(
    onLoginClick: (String, String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    isLoading: Boolean,
) {
    Box(
        modifier = Modifier
            .padding(bottom = 1.dp, start = 32.dp, end = 32.dp, top = 1.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .border(1.dp, Black)
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
                    text = stringResource(R.string.entrance_in_account),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

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
                            R.string.login
                        ),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            )
            var passwordText by rememberSaveable { mutableStateOf("") }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = passwordText,
                enabled = !isLoading,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { passwordText = it },
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
            Spacer(modifier = Modifier.height(4.dp))
            ForgotPassword {
                onForgotPasswordClick()
            }
            Spacer(modifier = Modifier.height(4.dp))
            ElevatedButtonLogin(enabled = !isLoading) {
                onLoginClick(loginText, passwordText)
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun DontHaveAccountYet(onTextClickListener: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .clickable {
                    onTextClickListener()
                },
            text = stringResource(R.string.dont_authorized),
            fontSize = 12.sp,
            fontFamily = FontFamily.Serif,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Justify,
            color = DeepSkyBlue
        )
    }
}

@Composable
fun ForgotPassword(onTextClickListener: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .clickable {
                    onTextClickListener()
                },
            text = stringResource(R.string.forgot_password),
            fontSize = 12.sp,
            fontFamily = FontFamily.Serif,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Justify,
            color = Color.Black
        )
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
fun ElevatedButtonLogin(
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