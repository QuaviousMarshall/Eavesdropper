package com.example.eavesdropper.presentation

import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eavesdropper.R
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.ui.theme.Aqua
import com.example.eavesdropper.ui.theme.DeepSkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCard(paddingValues: PaddingValues) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            AskMainIcon()
        }
        Row {
            val asks = listOf(
                Ask(0, "0 + 0", "0"),
                Ask(1, "1 + 0", "1"),
                Ask(2, "1 + 1", "2"),
                Ask(3, "1 + 2", "3"),
                Ask(4, "2 + 2", "4")
            )
            Last3AsksList(asks)
        }
        ElevatedButtonOn { }
        Spacer(Modifier.height(32.dp))
    }
}


@Composable
fun Last3AsksList(
    last3ASksList: List<Ask>
) {
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        initialValue = Color.LightGray,
        targetValue = MaterialTheme.colorScheme.onBackground,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .border(0.5.dp, Color.LightGray)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center,
            ) {
                Text(
                    text = stringResource(R.string.last_3_asks),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
            Spacer(Modifier.height(16.dp))
            Column {
                var size = last3ASksList.size - 1
                repeat(3) {
                    val ask = last3ASksList[size]
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .background(color)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = ask.question + "?",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Justify,
                            color = Color.Black
                        )
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = ": " + ask.answer,
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Justify,
                            color = Color.Black
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    size--
                }
            }
        }
    }
}

@Composable
fun AskMainIcon() {
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        initialValue = Aqua,
        targetValue = DeepSkyBlue,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val scale by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(3000),
                repeatMode = RepeatMode.Reverse,
            ),
    )
    Box(modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)) {
        Image(
            painter = painterResource(R.drawable.question_mark_circle_svg_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
        )
    }
}

@Composable
fun ElevatedButtonOn(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
    ) {
        ElevatedButton(
            onClick = { onClick() },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(
                stringResource(R.string.turn_on_thron),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
