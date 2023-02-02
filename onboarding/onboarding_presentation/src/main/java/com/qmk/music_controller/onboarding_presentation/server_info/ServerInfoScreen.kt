package com.qmk.music_controller.onboarding_presentation.server_info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.R
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun ServerInfoScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    viewModel: ServerInfoViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val port = state.port?.toString() ?: ""
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    Column(modifier) {
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = stringResource(id = com.qmk.music_controller.onboarding_presentation
                    .R.string.server_info_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = stringResource(id = com.qmk.music_controller.onboarding_presentation
                    .R.string.server_info_description),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.ip_address),
            fieldInitialValue = state.ip,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onFiledValueChange = {
                viewModel.onIPEnter(it)
            },
            hint = "192.168.0.1",
            shouldShowHint = state.isIPHintVisible,
            onFocusChanged = {
                viewModel.onIPFocusChange(it.isFocused)
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.port_number),
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
        Button(
            modifier = Modifier
                .padding(spacing.spaceSmall)
                .align(CenterHorizontally),
            onClick = viewModel::onNextClick
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}