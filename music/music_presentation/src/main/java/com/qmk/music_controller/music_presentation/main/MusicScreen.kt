package com.qmk.music_controller.music_presentation.main

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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.core_presentation.component.ErrorScreen
import com.qmk.music_controller.core_presentation.component.LoadingScreen
import com.qmk.music_controller.core_presentation.R
import com.qmk.music_controller.music_presentation.edit.EditMusicScreen
import com.qmk.music_controller.music_presentation.list.NewMusicScreen
import com.qmk.music_controller.naming_rule_presentation.add.AddNamingRuleScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen(
    modifier: Modifier = Modifier,
    viewModel: MusicViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
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
                else -> Unit
            }
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        when(state.processState) {
            ProcessState.STANDBY -> {
                NewMusicScreen(
                    modifier = Modifier.padding(padding),
                    newMusic = state.newMusic
                ) {
                    viewModel.onEditClick()
                }
            }
            ProcessState.EDITING -> {
                state.currentMusic?.let { music ->
                    val onPreviousClick: (() -> Unit)? =
                        if (state.currentIndex == 0) null
                        else { { viewModel.onPreviousMusicClick() } }
                    val onNextClick: (() -> Unit)? =
                        if (state.currentIndex == state.newMusic.lastIndex) null
                        else { { viewModel.onNextMusicClick() } }
                    EditMusicScreen(
                        modifier = Modifier.padding(padding),
                        music = music,
                        onTitleValueChange = { viewModel.onTitleValueChange(it) },
                        onArtistValueChange = { viewModel.onArtistValueChange(it) },
                        onSaveClick = { viewModel.onSaveValidationClick() },
                        onCancelClick = { viewModel.onCancelValidationClick() },
                        onAddRuleClick = { viewModel.onAddRuleClick() },
                        onPreviousClick = onPreviousClick,
                        onNextClick = onNextClick
                    )
                }
            }
            ProcessState.LOADING -> LoadingScreen(
            modifier = Modifier.fillMaxSize(),
            text = state.customMessage?.asString(context)
            )
            ProcessState.ERROR -> ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                text = state.customMessage?.asString(context),
                buttonText = stringResource(id = R.string.retry),
                onButtonClick = { viewModel.onReloadClick() }
            )
            ProcessState.ADDING_RULE -> {
                AddNamingRuleScreen(
                    viewModel = viewModel.namingRuleViewModel,
                    onNavigateUp = {
                        viewModel.onFinishAddingRule()
                    }
                )
            }
        }

    }
}