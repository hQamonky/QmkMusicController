package com.qmk.music_controller.setting_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.qmk.music_controller.core_presentation.LocalSpacing

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    label: String,
    labelStyle: TextStyle = LocalTextStyle.current,
    fieldInitialValue: String = "",
    fieldTextStyle: TextStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface),
    hint: String = "",
    shouldShowHint: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onFocusChanged: (FocusState) -> Unit = {},
    onFiledValueChange: (textFieldValue: String) -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = labelStyle
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        Box {
            BasicTextField(
                textStyle = fieldTextStyle,
                value = fieldInitialValue,
                onValueChange = onFiledValueChange,
                singleLine = true,
                keyboardOptions = keyboardOptions,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .padding(2.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
                    .padding(end = spacing.spaceMedium)
                    .onFocusChanged { onFocusChanged(it) }
            )
            if (shouldShowHint) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = spacing.spaceMedium)
                )
            }
        }
    }
}