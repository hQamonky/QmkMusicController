package com.qmk.music_controller.playlist_presentation.delete

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
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.playlist_presentation.R

@Composable
fun DeletePlaylistScreen(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    onConfirmClick: (playlist: Playlist) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = stringResource(id = R.string.delete_playlist),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = stringResource(id = R.string.confirm_delete_playlist, name)
        )
        Row {
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .weight(1f),
                onClick = { onCancelClick() }
            ) {
                Text(text = stringResource(
                    id = com.qmk.music_controller.core_presentation.R.string.cancel))
            }
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .weight(1f),
                onClick = { onConfirmClick(Playlist(id, name, emptyList())) }
            ) {
                Text(text = stringResource(
                    id = com.qmk.music_controller.core_presentation.R.string.confirm))
            }
        }
    }
}