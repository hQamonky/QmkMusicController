package com.qmk.music_controller.playlist_presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.R
import com.qmk.music_controller.playlist_presentation.main.component.RowComponent

@Composable
fun PlaylistsScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistsViewModel = hiltViewModel(),
    onAddPlaylistClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onEditClick: (id: String) -> Unit,
    onDeleteClick: (id: String) -> Unit,
    onDownloadPlaylistClick: (id: String) -> Unit
) {
    val state = viewModel.state
    val spacing = LocalSpacing.current
    when(state.loadingState) {
        LoadingState.LOADING -> {
            Box(modifier = modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(spacing.spaceSmall)
                    )
                    state.loadingState.message?.let {
                        Text(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(spacing.spaceSmall),
                            text = it
                        )
                    }
                }
            }
        }
        LoadingState.ERROR -> {
            Box(modifier = modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(spacing.spaceSmall),
                        text = stringResource(id = R.string.error),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    state.loadingState.message?.let {
                        Text(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(spacing.spaceSmall),
                            text = it
                        )
                    }
                    Button(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(spacing.spaceSmall),
                        onClick = { viewModel.onReloadClick() }
                    ) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
            }
        }
        LoadingState.STANDBY -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.spaceMedium),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.playlists),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Row {
                            IconButton(onClick = { onAddPlaylistClick() }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(
                                        id = com.qmk.music_controller.playlist_presentation.R.string
                                            .add_playlist)
                                )
                            }
                            IconButton(
                                onClick = { onDownloadClick() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_baseline_download_24),
                                    contentDescription = stringResource(id = R.string.download)
                                )
                            }
                            IconButton(onClick = { onArchiveClick() }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_baseline_archive_24),
                                    contentDescription = stringResource(
                                        id = com.qmk.music_controller.playlist_presentation.R.string
                                            .archive_music)
                                )
                            }
                        }
                    }
                }
                item {
                    Divider(modifier = Modifier.padding(bottom = spacing.spaceMedium))
                }
                items(state.playlists) { playlist ->
                    RowComponent(
                        playlist = playlist,
                        onEditClick = {
                            onEditClick(it.id)
                        },
                        onDeleteClick = {
                            onDeleteClick(it.id)
                        },
                        onDownloadClick = {
                            onDownloadPlaylistClick(it.id)
                        }
                    )
                }
            }
        }
    }
}