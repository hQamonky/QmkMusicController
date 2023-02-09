package com.qmk.music_controller.playlist_presentation.main.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.core_presentation.R

@Composable
fun RowComponent(
    playlist: Playlist,
    onEditClick: (playlist: Playlist) -> Unit,
    onDeleteClick: (playlist: Playlist) -> Unit,
    onDownloadClick: (playlist: Playlist) -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(
                start = spacing.spaceSmall,
                end = spacing.spaceSmall
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(start = spacing.spaceSmall)
                .align(CenterVertically),
            text = playlist.name
        )
        Row {
            IconButton(
                onClick = { onEditClick(playlist) }
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(
                onClick = { onDeleteClick(playlist) }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
            IconButton(
                onClick = { onDownloadClick(playlist) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_download_24),
                    contentDescription = "Download"
                )
            }
        }
    }
}