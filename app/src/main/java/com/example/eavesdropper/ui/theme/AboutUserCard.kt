package com.example.eavesdropper.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eavesdropper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUserCard() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                        text = stringResource(R.string.about_account),
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
                },
                scrollBehavior = scrollBehavior,
                expandedHeight = 60.dp
            )
        }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileInfoRow {
                ProfileInfoText(R.string.about_user_nickname)
            }

            ProfileInfoRow {
                ProfileInfoText(R.string.about_user_login)
            }

            ProfileInfoRow {
                ProfileInfoText(R.string.count_of_asks)
            }

            ProfileInfoRow {
                ProfileInfoText(R.string.account_creating_date)
            }
        }
    }
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
            .background(getColor())
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
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = Color.Black
    )
}

@Composable
fun Login() {
    Text(
        modifier = Modifier.padding(8.dp),
        text = stringResource(R.string.login) + ":",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = Color.Black
    )
}

@Composable
fun Nickname() {
    Text(
        modifier = Modifier.padding(8.dp),
        text = stringResource(R.string.nickname) + ":",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = Color.Black
    )
}

@Composable
fun AccountCreatingDate() {
    Text(
        modifier = Modifier.padding(8.dp),
        text = stringResource(R.string.account_creating_date),
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = Color.Black
    )
}

@Composable
fun CountOfAsks() {
    Text(
        modifier = Modifier.padding(8.dp),
        text = stringResource(R.string.count_of_asks),
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = Color.Black
    )
}





