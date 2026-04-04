package com.example.eavesdropper.presentation.screens.auth

import androidx.annotation.StringRes
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eavesdropper.R
import com.example.eavesdropper.presentation.ui.theme.DeepSkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationCard(
    onRegisterClick: (String, String) -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean,
    color: Color
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            RegistrationWelcomeText()
            Spacer(Modifier.weight(1f))

            NickLoginPasswordBox(
                onRegisterClick = onRegisterClick,
                onLoginClick = onLoginClick,
                isLoading = isLoading,
                color = color
            )

            Spacer(Modifier.weight(1f))

            VersionText()

        }
    }
}

@Composable
fun NickLoginPasswordBox(
    onRegisterClick: (String, String) -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean,
    color: Color
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
                    text = stringResource(R.string.registration),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            var loginText by rememberSaveable { mutableStateOf("") }
            ProfileTextField(
                value = loginText,
                onValueChange = { loginText = it },
                labelRes = R.string.login_types,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            Spacer(modifier = Modifier.height(12.dp))

            var passwordText by rememberSaveable { mutableStateOf("") }
            ProfileTextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                labelRes = R.string.password,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(8.dp))

            HaveAccountAlready {
                onLoginClick()
            }

            Spacer(modifier = Modifier.height(4.dp))

            ElevatedButtonRegin(enabled = !isLoading, color = color) {
                onRegisterClick(loginText, passwordText)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun HaveAccountAlready(onTextClickListener: () -> Unit) {
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
            text = stringResource(R.string.authorized_already),
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
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        label = {
            Text(
                text = stringResource(labelRes),
                fontSize = 10.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    )
}


@Composable
fun ElevatedButtonRegin(
    enabled: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
    ) {
        ElevatedButton(
            onClick = { onClick() },
            enabled = enabled,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = color,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(
                stringResource((R.string.reg_in)),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RegistrationWelcomeText() {
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