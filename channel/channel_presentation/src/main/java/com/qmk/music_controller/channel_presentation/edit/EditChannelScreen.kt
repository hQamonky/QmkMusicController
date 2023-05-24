package com.qmk.music_controller.channel_presentation.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.channel_presentation.R
import com.qmk.music_controller.core_presentation.component.ConfirmationDialog
import com.qmk.music_controller.setting_presentation.component.BooleanField
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun EditChannelScreen(
    modifier: Modifier = Modifier,
    separatorValue: String,
    onSeparatorFiledValueChange: (textFieldValue: String) -> Unit,
    artistBeforeTitleValue: Boolean,
    onArtistBeforeTitleChange: (value: Boolean) -> Unit,
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    ConfirmationDialog(
        modifier = modifier,
        title = stringResource(id = R.string.edit_channel),
        onConfirmClick = onConfirmClick,
        onCancelClick = onCancelClick
    ) {
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.separator),
            fieldInitialValue = separatorValue,
            onFiledValueChange = onSeparatorFiledValueChange,
            shouldShowHint = false
        )
        BooleanField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.artist_before_title),
            value = artistBeforeTitleValue,
            onValueChange = onArtistBeforeTitleChange
        )
    }
}