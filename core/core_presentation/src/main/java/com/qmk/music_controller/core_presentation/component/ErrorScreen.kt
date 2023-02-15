package com.qmk.music_controller.core_presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import com.qmk.music_controller.core_presentation.LocalSpacing

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    text: String? = null,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .padding(spacing.spaceSmall)
                    .align(CenterHorizontally),
                text = text ?: "Unknown Error."
            )
            if (onButtonClick != null && buttonText != null) {
                Button(
                    modifier = Modifier
                        .padding(spacing.spaceMedium)
                        .align(CenterHorizontally),
                    onClick = onButtonClick
                ) {
                    Text(text = buttonText)
                }
            }
        }
    }
}