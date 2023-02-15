package com.qmk.music_controller.music_presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.music_presentation.R
import com.qmk.music_controller.music_manager_domain.model.Music
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun EditMusicScreen(
    modifier: Modifier = Modifier,
    music: Music,
    onTitleValueChange: (textFieldValue: String) -> Unit,
    onArtistValueChange: (textFieldValue: String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onPreviousClick: (() -> Unit)?,
    onNextClick: (() -> Unit)?
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            text = stringResource(
                id = R.string.edit_music),
            style = MaterialTheme.typography.headlineMedium
        )
        Divider(modifier = Modifier.padding(bottom = spacing.spaceMedium))
        Row(modifier = Modifier.padding(spacing.spaceMedium)) {
            Text(
                text = "${music.filename}.${music.fileExtension}",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.title),
            fieldInitialValue = music.title,
            onFiledValueChange = onTitleValueChange
        )
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.artist),
            fieldInitialValue = music.artist,
            onFiledValueChange = onArtistValueChange
        )
        Row {
            if (onPreviousClick != null) {
                Button(
                    modifier = Modifier
                        .padding(spacing.spaceMedium)
                        .weight(1f),
                    onClick = { onPreviousClick() }
                ) {
                    Text(
                        text = stringResource(
                            id = com.qmk.music_controller.core_presentation.R.string.previous
                        )
                    )
                }
            }
            if (onNextClick != null) {
                Button(
                    modifier = Modifier
                        .padding(spacing.spaceMedium)
                        .weight(1f),
                    onClick = { onNextClick() }
                ) {
                    Text(
                        text = stringResource(
                            id = com.qmk.music_controller.core_presentation.R.string.next
                        )
                    )
                }
            }
        }
        Divider(
            modifier = Modifier.padding(
                top = spacing.spaceMedium,
                bottom = spacing.spaceMedium
            ),
            color = Color.LightGray
        )
        Button(
            modifier = Modifier
                .padding(
                    start = spacing.spaceMedium,
                    top = spacing.spaceMedium,
                    end = spacing.spaceMedium,
                    bottom = spacing.spaceSmall
                )
                .fillMaxWidth(),
            onClick = { onCancelClick() }
        ) {
            Text(
                text = stringResource(
                    id = com.qmk.music_controller.core_presentation.R.string.cancel
                )
            )
        }
        Button(
            modifier = Modifier
                .padding(
                    start = spacing.spaceMedium,
                    top = spacing.spaceSmall,
                    end = spacing.spaceMedium,
                    bottom = spacing.spaceMedium
                )
                .fillMaxWidth(),
            onClick = { onSaveClick() }
        ) {
            Text(
                text = stringResource(
                    id = com.qmk.music_controller.core_presentation.R.string.save
                )
            )
        }
    }
}