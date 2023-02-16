package com.qmk.music_controller.channel_presentation.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.qmk.music_controller.core_presentation.R
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.music_manager_domain.model.Channel
import com.qmk.music_controller.music_manager_domain.model.NamingRule

@Composable
fun ChannelRowComponent(
    channel: Channel,
    onEditClick: (channel: Channel) -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(
                start = spacing.spaceSmall,
                end = spacing.spaceSmall
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = spacing.spaceSmall)
                    .align(Alignment.CenterVertically),
                text = channel.name
            )
            Text(
                modifier = Modifier
                    .padding(start = spacing.spaceSmall)
                    .align(Alignment.CenterVertically),
                text = channel.namingFormat.separator
            )
            Text(
                modifier = Modifier
                    .padding(start = spacing.spaceSmall)
                    .align(Alignment.CenterVertically),
                text = stringResource(id =
                    if (channel.namingFormat.aristIsBeforeTitle) R.string.true_string
                    else R.string.false_string
                )
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = spacing.spaceMedium)
        ) {
            IconButton(
                onClick = { onEditClick(channel) }
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    }
}