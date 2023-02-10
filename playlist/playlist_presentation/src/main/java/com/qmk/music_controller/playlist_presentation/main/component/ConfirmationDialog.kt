package com.qmk.music_controller.playlist_presentation.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.R

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String,
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
        content()
        Row {
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .weight(1f),
                onClick = { onCancelClick() }
            ) {
                Text(
                    text = stringResource(
                        id = R.string.cancel
                    )
                )
            }
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .weight(1f),
                onClick = { onConfirmClick() }
            ) {
                Text(
                    text = stringResource(
                        id = R.string.confirm
                    )
                )
            }
        }
    }
}