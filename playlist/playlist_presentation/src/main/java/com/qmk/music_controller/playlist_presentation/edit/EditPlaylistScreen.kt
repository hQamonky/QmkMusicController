package com.qmk.music_controller.playlist_presentation.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.music_manager_domain.model.Playlist
import com.qmk.music_controller.playlist_presentation.R
import com.qmk.music_controller.playlist_presentation.main.component.ConfirmationDialog
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun EditPlaylistScreen(
    modifier: Modifier = Modifier,
    name: String,
    shouldShowHint: Boolean = false,
    onFocusChanged: (FocusState) -> Unit,
    onFiledValueChange: (textFieldValue: String) -> Unit,
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    ConfirmationDialog(
        modifier = modifier,
        title = stringResource(id = R.string.edit_playlist),
        onConfirmClick = onConfirmClick,
        onCancelClick = onCancelClick
    ) {
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = com.qmk.music_controller.core_presentation.R.string.name),
            fieldInitialValue = name,
            onFiledValueChange = onFiledValueChange,
            hint = "Best of WilliTracks",
            shouldShowHint = shouldShowHint,
            onFocusChanged = onFocusChanged
        )
    }
}