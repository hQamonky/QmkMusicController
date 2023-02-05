package com.qmk.music_controller.setting_presentation.display

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.setting_presentation.R

@Composable
fun DisplaySettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: DisplaySettingsViewModel = hiltViewModel(),
    onEditServerInfoClick: () -> Unit,
    onEditServerSettingsClick: () -> Unit,
    onUpdateYoutubeDlClick: () -> Unit,
    onFactoryResetClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val state = viewModel.state
    val scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState, enabled = true)
    ) {
        TitleElement(
            title = stringResource(id = R.string.server_info_title)
        ) { onEditServerInfoClick() }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        TextElement(
            name = stringResource(
                id = com.qmk.music_controller.core_presentation.R.string.ip_address),
            value = state.ipAddress.asString(context)
        )
        TextElement(
            name = stringResource(
                id = com.qmk.music_controller.core_presentation.R.string.port_number),
            value = state.portNumber.asString(context)
        )
        Divider(modifier = Modifier.padding(spacing.spaceSmall), color = Color.LightGray)
        TitleElement(
            title = stringResource(id = R.string.server_configuration_title)
        ) { onEditServerSettingsClick() }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        TextElement(
            name = stringResource(id = R.string.music_directory_title),
            value = state.musicDirectory.asString(context)
        )
        TextElement(
            name = stringResource(id = R.string.download_occurrence_title),
            value = state.downloadOccurrence.asString(context)
        )
        TextElement(
            name = stringResource(id = R.string.auto_download_title),
            value = state.autoDownload.asString(context)
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Button(
            modifier = Modifier
                .padding(spacing.spaceMedium)
                .fillMaxWidth(),
            onClick = { onUpdateYoutubeDlClick() }
        ) {
            Text(text = stringResource(id = R.string.update_youtube_dl))
        }
        Button(
            modifier = Modifier
                .padding(spacing.spaceMedium)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            onClick = { onFactoryResetClick() }
        ) {
            Text(text = stringResource(id = R.string.factory_reset))
        }
    }
}

@Composable
private fun TitleElement(
    modifier: Modifier = Modifier,
    title: String,
    onEditClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(spacing.spaceMedium)
                .align(Alignment.CenterVertically),
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            modifier = Modifier
                .padding(spacing.spaceMedium)
                .align(Alignment.CenterVertically),
            onClick = { onEditClick() }
        ) {
            Text(text = stringResource(
                id = com.qmk.music_controller.core_presentation.R.string.edit))
        }
    }
}

@Composable
private fun TextElement(
    modifier: Modifier = Modifier,
    name: String,
    value: String
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = name
        )
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = value
        )
    }
}