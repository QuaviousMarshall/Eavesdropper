package com.example.eavesdropper.presentation.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import com.example.eavesdropper.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.presentation.screens.main.AskRow
import com.example.eavesdropper.presentation.ui.theme.myColor
import com.example.eavesdropper.presentation.viewmodels.ListOfAsksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfAsksCard(
    paddingValues: PaddingValues
) {
    val viewModel: ListOfAsksViewModel = hiltViewModel()
    val asks by viewModel.filteredAsks.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            AskSearchField(
                query = query,
                onQueryChange = viewModel::onSearchQueryChange
            )
        }

        items(asks, key = { it.id }) { ask ->

            val dismissBoxState = rememberSwipeToDismissBoxState(
                positionalThreshold = { it * 0.5f },
                confirmValueChange = { value ->
                    when (value) {
                        SwipeToDismissBoxValue.StartToEnd -> {
                            viewModel.getDetailedAnswer(ask, ask.question)
                            false
                        }

                        SwipeToDismissBoxValue.EndToStart -> {
                            viewModel.delete(ask)
                            true
                        }

                        SwipeToDismissBoxValue.Settled -> {
                            false
                        }
                    }
                }
            )

            SwipeToDismissBox(
                modifier = Modifier.animateItem(),
                state = dismissBoxState,
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = true,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(myColor()),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight(),
                                imageVector = Icons.Default.Info,
                                contentDescription = "Delete",
                                tint = Color.Green
                            )

                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight(),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }
            ) {
                AskRowInList(ask = ask)
            }
        }
    }
}

@Composable
fun AskSearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val color = myColor()

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text(text = stringResource(R.string.find_ask)) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onQueryChange("") }
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = color,
            unfocusedBorderColor = color,

            focusedTrailingIconColor = color,
            unfocusedTrailingIconColor = color,

            focusedLeadingIconColor = color,
            unfocusedLeadingIconColor = color,

            focusedPlaceholderColor = color,
            unfocusedPlaceholderColor = color,

            cursorColor = Color.Black,
            focusedTextColor = color,
            unfocusedTextColor = color
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}

@Composable
fun AskRowInList(ask: Ask) {

    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(containerColor = myColor()),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 86.dp)
            .clickable { isExpanded = !isExpanded }
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${ask.question}?",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.W500,
                    color = Color.Black,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        imageVector = if (isExpanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = Color.Black
                    )
                }
            }
        }
        AnimatedVisibility(visible = isExpanded) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = ": ${ask.answer}",
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.W500,
                color = Color.Black
            )
        }
    }
}