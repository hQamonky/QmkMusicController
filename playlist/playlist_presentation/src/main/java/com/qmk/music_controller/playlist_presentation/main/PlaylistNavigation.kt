package com.qmk.music_controller.playlist_presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.core_presentation.component.ErrorScreen
import com.qmk.music_controller.core_presentation.component.LoadingScreen
import com.qmk.music_controller.playlist_presentation.add.AddPlaylistScreen
import com.qmk.music_controller.playlist_presentation.archive.ArchiveMusicScreen
import com.qmk.music_controller.playlist_presentation.delete.DeletePlaylistScreen
import com.qmk.music_controller.playlist_presentation.download.DownloadPlaylistScreen
import com.qmk.music_controller.playlist_presentation.download.DownloadPlaylistsScreen
import com.qmk.music_controller.playlist_presentation.edit.EditPlaylistScreen
import com.qmk.music_controller.playlist_presentation.list.LoadingState
import com.qmk.music_controller.playlist_presentation.list.PlaylistsScreen
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.ADD_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.ARCHIVE_MUSIC
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.DELETE_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.DOWNLOAD_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.DOWNLOAD_PLAYLISTS
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.EDIT_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.LIST
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistNavigation(
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(event.message.asString(context))
                    }
                }
                is UiEvent.NavigateTo -> {
                    navController.navigate(event.route)
                }
                is UiEvent.NavigateUp -> navController.navigateUp()
                else -> Unit
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        when(state.loadingState) {
            LoadingState.STANDBY -> NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = LIST
            ) {
                composable(LIST) {
                    PlaylistsScreen(
                        onAddPlaylistClick = { navController.navigate(ADD_PLAYLIST) },
                        onArchiveClick = { navController.navigate(ARCHIVE_MUSIC) },
                        onDownloadClick = { navController.navigate(DOWNLOAD_PLAYLISTS) },
                        onEditClick = { viewModel.getPlaylistForEdit(it) },
                        onDeleteClick = { viewModel.getPlaylistForDelete(it) },
                        onDownloadPlaylistClick = { viewModel.getPlaylistForDownload(it) }
                    )
                }
                composable(ADD_PLAYLIST) {
                    AddPlaylistScreen {
                        it?.let {
                            scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                        navController.navigate(LIST)
                    }
                }
                composable(EDIT_PLAYLIST) {
                    state.processingPlaylist?.let { playlist ->
                        EditPlaylistScreen(
                            name = state.editPlaylistState.name,
                            shouldShowHint = state.editPlaylistState.isNameHintVisible,
                            onFocusChanged = {
                                viewModel.onEditNameFocusChange(it.isFocused)
                            },
                            onFiledValueChange = {
                                viewModel.onEditNameEnter(it)
                            },
                            onConfirmClick = {
                                viewModel.editPlaylist(playlist)
                            },
                            onCancelClick = { navController.navigate(LIST) }
                        )
                    }
                }
                composable(DELETE_PLAYLIST) {
                    state.processingPlaylist?.let { playlist ->
                        DeletePlaylistScreen(
                            playlist = playlist,
                            onConfirmClick = {
                                viewModel.deletePlaylist(playlist)
                            },
                            onCancelClick = { navController.navigate(LIST) }
                        )
                    }
                }
                composable(DOWNLOAD_PLAYLIST) {
                    state.processingPlaylist?.let { playlist ->
                        DownloadPlaylistScreen(
                            playlist = playlist,
                            onConfirmClick = {
                                viewModel.downloadPlaylist(playlist)
                            },
                            onCancelClick = { navController.navigate(LIST) }
                        )
                    }
                }
                composable(DOWNLOAD_PLAYLISTS) {
                    DownloadPlaylistsScreen(
                        onConfirmClick = {
                            viewModel.downloadPlaylists()
                        },
                        onCancelClick = { navController.navigate(LIST) }
                    )
                }
                composable(ARCHIVE_MUSIC) {
                    ArchiveMusicScreen(
                        onConfirmClick = {
                            viewModel.archiveMusic()
                        },
                        onCancelClick = { navController.navigate(LIST) }
                    )
                }
            }
            LoadingState.LOADING -> LoadingScreen(
                modifier = Modifier.fillMaxSize(),
                text = state.customMessage?.asString(context)
            )
            LoadingState.ERROR -> ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                text = state.customMessage?.asString(context)
            )
        }
    }
}