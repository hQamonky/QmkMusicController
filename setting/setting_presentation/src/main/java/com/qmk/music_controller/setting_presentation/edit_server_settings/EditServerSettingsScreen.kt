package com.qmk.music_controller.setting_presentation.edit_server_settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.setting_presentation.R
import com.qmk.music_controller.setting_presentation.component.BooleanField
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun EditServerSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: EditServerSettingsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    val state = viewModel.state
    val downloadOccurrence = state.downloadOccurrence?.toString() ?: ""
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> onNavigateUp()
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
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState, enabled = true)
        ) {
            Row {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = { viewModel.onBackClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(
                            id = com.qmk.music_controller.core_presentation.R.string.back
                        )
                    )
                }
                Text(
                    modifier = Modifier.padding(spacing.spaceMedium),
                    text = stringResource(id = R.string.server_configuration_title),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                label = stringResource(id = R.string.download_occurrence_title),
                fieldInitialValue = downloadOccurrence,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onFiledValueChange = {
                    viewModel.onDlOccurrenceEnter(it)
                },
                hint = "60",
                shouldShowHint = state.isDlOccurrenceHintVisible,
                onFocusChanged = {
                    viewModel.onDlOccurrenceFocusChange(it.isFocused)
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                label = stringResource(id = R.string.music_directory_title),
                fieldInitialValue = state.musicDirectory,
                onFiledValueChange = {
                    viewModel.onMusicDirEnter(it)
                },
                hint = "/home/qmk/Music",
                shouldShowHint = state.isMusicDirHintVisible,
                onFocusChanged = {
                    viewModel.onMusicDirFocusChange(it.isFocused)
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            BooleanField(
                label = stringResource(id = R.string.auto_download_title),
                value = state.autoDownload,
                onValueChange = {
                    viewModel.onAutoDlChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .align(Alignment.CenterHorizontally),
                onClick = { viewModel.onSaveClick() }
            ) {
                Text(
                    text = stringResource(
                        id = com.qmk.music_controller.core_presentation.R.string.save
                    )
                )
            }
        }
    }
}