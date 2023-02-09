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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.core_presentation.component.ErrorScreen
import com.qmk.music_controller.core_presentation.component.LoadingScreen
import com.qmk.music_controller.playlist_presentation.add.AddPlaylistScreen
import com.qmk.music_controller.playlist_presentation.delete.DeletePlaylistScreen
import com.qmk.music_controller.playlist_presentation.list.PlaylistsScreen
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.ADD_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.ARCHIVE_MUSIC
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.DELETE_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.DOWNLOAD_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.DOWNLOAD_PLAYLISTS
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.EDIT_PLAYLIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.ERROR
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.LIST
import com.qmk.music_controller.playlist_presentation.main.PlaylistRoute.LOADING
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
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = LIST
        ) {
            composable(LOADING) {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize(),
                    text = state.customMessage?.asString(context)
                )
            }
            composable(ERROR) {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    text = state.customMessage?.asString(context)
                )
            }
            composable(LIST) {
                PlaylistsScreen(
                    onAddPlaylistClick = { navController.navigate(ADD_PLAYLIST) },
                    onArchiveClick = { navController.navigate(ARCHIVE_MUSIC) },
                    onDownloadClick = { navController.navigate(DOWNLOAD_PLAYLISTS) },
                    onEditClick = { navController.navigate("$EDIT_PLAYLIST/$it") },
                    onDeleteClick = {
                        viewModel.getPlaylist(it) { playlist -> navController.navigate(
                            "$DELETE_PLAYLIST/${playlist.id}/${playlist.name}"
                        ) }
                    },
                    onDownloadPlaylistClick = { navController.navigate("$DOWNLOAD_PLAYLIST/$it") }
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
            composable(
                route = "$EDIT_PLAYLIST/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    },
                )
            ) {

            }
            composable(
                route = "$DELETE_PLAYLIST/{id}/{name}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    },
                    navArgument("name") {
                        type = NavType.StringType
                    }
                )
            ) {
                val id = it.arguments?.getString("id")!!
                val name = it.arguments?.getString("name")!!
                DeletePlaylistScreen(
                    id = id,
                    name = name,
                    onConfirmClick = { playlist ->
                        viewModel.deletePlaylist(playlist)
                    },
                    onCancelClick = { navController.navigate(LIST) }
                )
            }
            composable(
                route = "$DOWNLOAD_PLAYLIST/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    },
                )
            ) {

            }
            composable(DOWNLOAD_PLAYLISTS) {

            }
            composable(ARCHIVE_MUSIC) {

            }
        }
    }
}