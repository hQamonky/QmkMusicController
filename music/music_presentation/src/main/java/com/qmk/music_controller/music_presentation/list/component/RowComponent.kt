package com.qmk.music_controller.music_presentation.list.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.music_manager_domain.model.Music

@Composable
fun RowComponent(
    music: Music
) {
    val spacing = LocalSpacing.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(
                start = spacing.spaceMedium,
                top = spacing.spaceMedium,
                end = spacing.spaceMedium,
                bottom = spacing.spaceExtraSmall
            ),
            text = "${music.filename}.${music.fileExtension}",
            style = MaterialTheme.typography.headlineSmall
        )
        Row(
            modifier = Modifier
                .padding(
                    start = spacing.spaceMedium,
                    top = spacing.spaceExtraSmall,
                    end = spacing.spaceMedium,
                    bottom = spacing.spaceMedium
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = music.title
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = music.artist
            )
        }
        Divider(
            modifier = Modifier.padding(
                top = spacing.spaceSmall,
                bottom = spacing.spaceSmall
            ),
            color = Color.LightGray
        )
    }
}