package com.qmk.music_controller.setting_presentation.edit_server_info

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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.setting_presentation.R
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun EditServerInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: EditServerInfoViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val spacing = LocalSpacing.current
    val scrollState = rememberScrollState()
    val state = viewModel.state
    val port = state.portNumber?.toString() ?: ""
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
                    modifier = Modifier.align(CenterVertically),
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
                    text = stringResource(id = R.string.server_info_title),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                label = stringResource(id = com.qmk.music_controller.core_presentation.R.string.ip_address),
                fieldInitialValue = state.ipAddress,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onFiledValueChange = {
                    viewModel.onIPEnter(it)
                },
                hint = "192.168.0.1",
                shouldShowHint = state.isIpHintVisible,
                onFocusChanged = {
                    viewModel.onIPFocusChange(it.isFocused)
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                label = stringResource(id = com.qmk.music_controller.core_presentation.R.string.port_number),
                fieldInitialValue = port,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onFiledValueChange = {
                    viewModel.onPortEnter(it)
                },
                hint = "8092",
                shouldShowHint = state.isPortHintVisible,
                onFocusChanged = {
                    viewModel.onPortFocusChange(it.isFocused)
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .align(CenterHorizontally),
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