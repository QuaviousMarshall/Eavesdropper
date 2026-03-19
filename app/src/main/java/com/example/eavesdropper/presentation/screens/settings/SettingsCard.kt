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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.eavesdropper.R
import com.example.eavesdropper.data.remote.model.AiModel
import com.example.eavesdropper.presentation.screens.auth.VersionText
import com.example.eavesdropper.presentation.ui.theme.Aqua
import com.example.eavesdropper.presentation.ui.theme.Black
import com.example.eavesdropper.presentation.ui.theme.DeepSkyBlue
import com.example.eavesdropper.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsCard(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    onProfileButtonClick: () -> Unit,
    onAppInfoButtonClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var onLastAsksClicked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedOptionForAsks by remember { mutableIntStateOf(viewModel.n.value) }

    val currentModel by viewModel.aiModel.collectAsState()

    var selectedModel by remember(currentModel) {
        mutableStateOf(currentModel)
    }

    if (showDialog) {
        ShowAiModelToChooseAlertDialog(
            selectedModel = selectedModel,
            onOptionSelected = { selectedModel = it },
            onOpenWindowToChooseClicked = { showDialog = false },
            onSaveClick = {
                viewModel.setAiModel(it)
                showDialog = false
            }
        )
    }

    if (onLastAsksClicked) {

        ShowCountOfAsksAlertDialog(
            onLastAsksClicked = { onLastAsksClicked = false },
            selectedOption = selectedOptionForAsks,
            onOptionSelected = {
                selectedOptionForAsks = it
            },
        ) {
            viewModel.setNewDigitOfLastAsks(it)
            onLastAsksClicked = false
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        ProfileRow {
            ProfileActionButton(
                R.string.about_account,
                onProfileButtonClick
            )
        }
        ProfileRow {
            ProfileActionButton(
                R.string.count_of_last_asks,
                onProfileButtonClick = {
                    onLastAsksClicked = true
                }
            )
        }
        ProfileRow {
            ProfileActionButton(
                R.string.choice_of_ai_model,
                onProfileButtonClick = {
                    showDialog = true
                }
            )
        }
        ProfileRow {
            ProfileActionButton(
                R.string.about_app,
                onAppInfoButtonClick
            )
        }
        ProfileRow {
            ProfileActionButton(
                R.string.log_out_button,
                onLogoutClick
            )
        }
        Spacer(Modifier.weight(1f))
        VersionText()
    }
}

@Composable
fun ShowAiModelToChooseAlertDialog(
    onOpenWindowToChooseClicked: () -> Unit,
    selectedModel: AiModel,
    onOptionSelected: (AiModel) -> Unit,
    onSaveClick: (AiModel) -> Unit

) {
    val options = listOf(
        AiModel.GIGACHAT to stringResource(R.string.gigachat),
        AiModel.OPENAI to stringResource(R.string.openai)
    )

    AlertDialog(
        onDismissRequest = onOpenWindowToChooseClicked,

        title = {
            Text(stringResource(R.string.enter_ai_model_to_work))
        },

        text = {
            Column(Modifier.selectableGroup()) {
                options.forEach { (model, title) ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = model == selectedModel,
                                onClick = { onOptionSelected(model) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            colors = RadioButtonColors(DeepSkyBlue, Black, Aqua, Black),
                            selected = model == selectedModel,
                            onClick = { onOptionSelected(model) },
                        )
                        Text(
                            text = title,
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                }
            }
        },

        confirmButton = {
            TextButton(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(aboutUserGetColor()),
                onClick = {
                    onSaveClick(selectedModel)
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

@Composable
fun ShowCountOfAsksAlertDialog(
    onLastAsksClicked: () -> Unit,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
    onSaveClick: (Int) -> Unit
) {
    val radioOptions = listOf(1, 2, 3)

    AlertDialog(
        onDismissRequest = onLastAsksClicked,

        title = {
            Text(stringResource(R.string.enter_count_of_last_asks))
        },

        text = {
            Column(Modifier.selectableGroup()) {
                radioOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = option == selectedOption,
                                onClick = { onOptionSelected(option) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            colors = RadioButtonColors(DeepSkyBlue, Black, Aqua, Black),
                            selected = option == selectedOption,
                            onClick = { onOptionSelected(option) }
                        )
                        Text(
                            text = option.toString(),
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                }
            }
        },

        confirmButton = {
            TextButton(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(aboutUserGetColor()),
                onClick = {
                    onSaveClick(selectedOption)
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

@Composable
fun ProfileActionButton(
    @StringRes textRes: Int,
    onProfileButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onProfileButtonClick,
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
            textAlign = TextAlign.Center,
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