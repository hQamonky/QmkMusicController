package com.qmk.music_controller.music_presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.core_presentation.R
import com.qmk.music_controller.music_manager_domain.model.Music
import com.qmk.music_controller.music_presentation.list.component.RowComponent

@Composable
fun NewMusicScreen(
    modifier: Modifier = Modifier,
    newMusic: List<Music>,
    onValidateClick: () -> Unit
) {
    val spacing = LocalSpacing.current
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
                    text = stringResource(id = R.string.new_music),
                    style = MaterialTheme.typography.headlineMedium
                )
                Button(
                    onClick = { onValidateClick() }
                ) {
                    Text(text = stringResource(id = R.string.validate))
                }
            }
        }
        item {
            Divider(modifier = Modifier.padding(bottom = spacing.spaceMedium))
        }
        items(newMusic) { music ->
            RowComponent(music = music)
        }
    }
}