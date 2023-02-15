package com.qmk.music_controller.naming_rule_presentation.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.R
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun AddNamingRuleScreen(
    modifier: Modifier = Modifier,
    viewModel: AddNamingRuleViewModel = hiltViewModel(),
    onNavigateUp: (savedResult: String?) -> Unit
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val state = viewModel.state
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> {
                    onNavigateUp(state.savedResult?.asString(context))
                }
                else -> Unit
            }
        }
    }
    if (state.processState == ProcessState.PROCESSING) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else if (state.processState == ProcessState.ENTERING_FIELDS) {
        Column(
            modifier = modifier
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                text = stringResource(
                    id = com.qmk.music_controller.naming_rule_presentation.R.string.add_naming_rule),
                style = MaterialTheme.typography.headlineMedium
            )
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                label = stringResource(
                    id = com.qmk.music_controller.naming_rule_presentation.R.string.replace),
                fieldInitialValue = state.replace,
                onFiledValueChange = {
                    viewModel.onReplaceEnter(it)
                },
                hint = " Lyrics",
                shouldShowHint = state.isReplaceHintVisible,
                onFocusChanged = {
                    viewModel.onReplaceFocusChange(it.isFocused)
                }
            )
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                label = stringResource(
                    id = com.qmk.music_controller.naming_rule_presentation.R.string.replace_by),
                fieldInitialValue = state.replaceBy,
                onFiledValueChange = {
                    viewModel.onReplaceByEnter(it)
                },
                shouldShowHint = false
            )
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                label = stringResource(
                    id = com.qmk.music_controller.naming_rule_presentation.R.string.priority),
                fieldInitialValue = state.priority,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onFiledValueChange = {
                    viewModel.onPriorityEnter(it)
                },
                hint = "2",
                shouldShowHint = state.isPriorityHintVisible,
                onFocusChanged = {
                    viewModel.onPriorityFocusChange(it.isFocused)
                }
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(
                    modifier = Modifier
                        .padding(spacing.spaceSmall)
                        .weight(1f),
                    onClick = { onNavigateUp(null) }
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.cancel
                        )
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(spacing.spaceSmall)
                        .weight(1f),
                    onClick = { viewModel.onSaveClick() }
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.save
                        )
                    )
                }
            }
        }
    }
}