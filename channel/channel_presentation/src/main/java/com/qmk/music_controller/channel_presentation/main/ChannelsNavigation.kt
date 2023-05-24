package com.qmk.music_controller.channel_presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.qmk.music_controller.channel_presentation.edit.EditChannelScreen
import com.qmk.music_controller.channel_presentation.list.LoadingState
import com.qmk.music_controller.channel_presentation.list.ChannelsScreen
import com.qmk.music_controller.channel_presentation.main.ChannelRoute.EDIT_CHANNEL
import com.qmk.music_controller.channel_presentation.main.ChannelRoute.LIST
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.core_presentation.component.ErrorScreen
import com.qmk.music_controller.core_presentation.component.LoadingScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelsNavigation(
    viewModel: ChannelViewModel = hiltViewModel()
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
                    ChannelsScreen(
                        onEditClick = { viewModel.getChannelForEdit(it) }
                    )
                }
                composable(EDIT_CHANNEL) {
                    state.processingChannel?.let { channel ->
                        EditChannelScreen(
                            separatorValue = state.editChannelState.separator,
                            artistBeforeTitleValue = state.editChannelState.isArtistBeforeTitle,
                            onSeparatorFiledValueChange = { viewModel.onEditSeparatorEnter(it) },
                            onArtistBeforeTitleChange = { viewModel.onArtistBeforeTitleChange(it) },
                            onConfirmClick = {
                                viewModel.editChannel(channel)
                            },
                            onCancelClick = { navController.navigate(LIST) }
                        )
                    }
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