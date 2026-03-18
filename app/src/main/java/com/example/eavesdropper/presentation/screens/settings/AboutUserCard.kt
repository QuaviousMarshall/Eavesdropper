package com.example.eavesdropper.presentation.screens.settings

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.eavesdropper.R
import com.example.eavesdropper.presentation.ui.theme.Aqua
import com.example.eavesdropper.presentation.ui.theme.Black
import com.example.eavesdropper.presentation.ui.theme.DeepSkyBlue
import com.example.eavesdropper.presentation.viewmodels.AuthViewModel
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
    val userInfo by viewModel.userInfo.collectAsState()

    var showNicknameDialog by remember { mutableStateOf(false) }
    var nickname by remember { mutableStateOf("") }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    val avatarLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            avatarUri = it
            viewModel.uploadAvatar(it)
        }
    }
    val listViewModel: ListOfAsksViewModel = hiltViewModel()
    val asks by listViewModel.asks.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {

        if (showNicknameDialog) {

            AlertDialog(
                onDismissRequest = { showNicknameDialog = false },

                title = {
                    Text(stringResource(R.string.enter_nickname))
                },

                text = {
                    OutlinedTextField(
                        modifier = Modifier.background(aboutUserGetColor()),
                        value = nickname,
                        enabled = showNicknameDialog,
                        onValueChange = { nickname = it },
                        singleLine = true
                    )
                },

                confirmButton = {
                    TextButton(
                        modifier = Modifier
                            .background(aboutUserGetColor())
                            .clip(shape = RoundedCornerShape(16.dp)),
                        onClick = {
                            viewModel.addNickname(nickname)
                            showNicknameDialog = false
                        }
                    ) {
                        Text(
                            stringResource(R.string.save),
                            color = Black,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = avatarUri ?: userInfo?.urlPhoto,
                contentDescription = "avatar",
                placeholder = painterResource(R.drawable.ic_avatar_placeholder),
                error = painterResource(R.drawable.ic_avatar_placeholder),
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable {
                        avatarLauncher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoRow(
            modifier = Modifier.clickable(userInfo?.nickname.isNullOrBlank()) {
                nickname = ""
                showNicknameDialog = true
            }
        ) {

            ProfileInfoText(
                R.string.about_user_nickname,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = userInfo?.nickname ?: "-",
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

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
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
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





