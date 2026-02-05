package com.example.eavesdropper.presentation.screens.list

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eavesdropper.R
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.presentation.viewmodels.ListOfAsksViewModel
import com.example.eavesdropper.presentation.ui.theme.Aqua
import com.example.eavesdropper.presentation.ui.theme.DeepSkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfAsksCard(
    paddingValues: PaddingValues
) {
    val viewModel: ListOfAsksViewModel = hiltViewModel()
    val asks by viewModel.asks.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(asks, key = { it.id }) { ask ->

            val dismissBoxState = rememberSwipeToDismissBoxState(
                positionalThreshold = { it * 0.5f },
                confirmValueChange = { value ->
                    val isDismissed = value in setOf(

                        SwipeToDismissBoxValue.StartToEnd,
                        SwipeToDismissBoxValue.EndToStart
                    )
                    if (isDismissed) {
                        viewModel.delete(ask)
                    }
                    return@rememberSwipeToDismissBoxState isDismissed
                }
            )

            SwipeToDismissBox(
                modifier = Modifier.animateItem(),
                state = dismissBoxState,
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(listOfAsksGetColor()),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(R.string.delete_ask),
                            fontFamily = FontFamily.Serif,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                }
            ) {
                Ask(ask = ask)
            }
        }
    }
}


@Composable
private fun Ask(
    ask: Ask
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(listOfAsksGetColor())
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        top = 2.dp,
                        end = 4.dp,
                        bottom = 1.dp
                    ),
                    text = ask.question + "?",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        top = 1.dp,
                        end = 4.dp,
                        bottom = 2.dp
                    ),
                    text = ": " + ask.answer,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

        }
    }
}

@Composable
fun listOfAsksGetColor(): Color {
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