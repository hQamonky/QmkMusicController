package com.qmk.music_controller.channel_presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.channel_presentation.list.component.ChannelRowComponent
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.R

@Composable
fun ChannelsScreen(
    modifier: Modifier = Modifier,
    viewModel: ChannelsViewModel = hiltViewModel(),
    onEditClick: (id: String) -> Unit
) {
    val state = viewModel.state
    val spacing = LocalSpacing.current
    when(state.loadingState) {
        LoadingState.LOADING -> {
            Box(modifier = modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(spacing.spaceSmall)
                    )
                    state.loadingState.message?.let {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
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
                            .align(Alignment.CenterHorizontally)
                            .padding(spacing.spaceSmall),
                        text = stringResource(id = R.string.error),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    state.loadingState.message?.let {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(spacing.spaceSmall),
                            text = it
                        )
                    }
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
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
                            text = stringResource(id = R.string.channels),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                item {
                    Divider(modifier = Modifier.padding(bottom = spacing.spaceMedium))
                }
                items(state.channels) { channel ->
                    ChannelRowComponent(
                        channel = channel,
                        onEditClick = {
                            onEditClick(it.id)
                        }
                    )
                }
            }
        }
    }
}