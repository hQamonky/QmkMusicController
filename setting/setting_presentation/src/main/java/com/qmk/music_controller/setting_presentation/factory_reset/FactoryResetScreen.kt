package com.qmk.music_controller.setting_presentation.factory_reset

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.setting_presentation.R

@Composable
fun FactoryResetScreen(
    modifier: Modifier = Modifier,
    viewModel: FactoryResetViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val spacing = LocalSpacing.current
    val isLoading = viewModel.isLoading
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        Column(
            modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(spacing.spaceSmall),
                text = stringResource(id = R.string.factory_reset),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(spacing.spaceSmall),
                text = stringResource(id = R.string.factory_reset_description)
            )
            Row(
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Button(
                    modifier = Modifier.padding(spacing.spaceSmall),
                    onClick = { onNavigateUp() }
                ) {
                    Text(text = stringResource(
                        id = com.qmk.music_controller.core_presentation.R.string.cancel))
                }
                Button(
                    modifier = Modifier.padding(spacing.spaceSmall),
                    onClick = { viewModel.onConfirm() }
                ) {
                    Text(text = stringResource(
                        id = com.qmk.music_controller.core_presentation.R.string.confirm))
                }
            }
        }
    }
}