package com.qmk.music_controller.core_presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.qmk.music_controller.core_presentation.LocalSpacing

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    text: String? = null
) {
    val spacing = LocalSpacing.current
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(spacing.spaceSmall),
            text = text ?: "Unknown Error."
        )
    }
}