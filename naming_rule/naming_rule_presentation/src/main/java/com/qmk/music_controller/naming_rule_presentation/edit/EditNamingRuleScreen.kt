package com.qmk.music_controller.naming_rule_presentation.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.naming_rule_presentation.R
import com.qmk.music_controller.core_presentation.component.ConfirmationDialog
import com.qmk.music_controller.setting_presentation.component.SimpleTextField

@Composable
fun EditNamingRuleScreen(
    modifier: Modifier = Modifier,
    replaceValue: String,
    onReplaceFiledValueChange: (textFieldValue: String) -> Unit,
    replaceByValue: String,
    onReplaceByFiledValueChange: (textFieldValue: String) -> Unit,
    priorityValue: String,
    onPriorityFiledValueChange: (textFieldValue: String) -> Unit,
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    ConfirmationDialog(
        modifier = modifier,
        title = stringResource(id = R.string.edit_naming_rule),
        onConfirmClick = onConfirmClick,
        onCancelClick = onCancelClick
    ) {
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.replace),
            fieldInitialValue = replaceValue,
            onFiledValueChange = onReplaceFiledValueChange,
            shouldShowHint = false
        )
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.replace_by),
            fieldInitialValue = replaceByValue,
            onFiledValueChange = onReplaceByFiledValueChange,
            shouldShowHint = false
        )
        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            label = stringResource(id = R.string.priority),
            fieldInitialValue = priorityValue,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onFiledValueChange = onPriorityFiledValueChange,
            shouldShowHint = false
        )
    }
}