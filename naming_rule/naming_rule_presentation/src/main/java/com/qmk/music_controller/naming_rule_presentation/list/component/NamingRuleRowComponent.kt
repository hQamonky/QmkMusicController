package com.qmk.music_controller.naming_rule_presentation.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.music_manager_domain.model.NamingRule

@Composable
fun NamingRuleRowComponent(
    namingRule: NamingRule,
    onEditClick: (namingRule: NamingRule) -> Unit,
    onDeleteClick: (namingRule: NamingRule) -> Unit
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
                text = namingRule.replace
            )
            Text(
                modifier = Modifier
                    .padding(start = spacing.spaceSmall)
                    .align(Alignment.CenterVertically),
                text = namingRule.replaceBy
            )
            Text(
                modifier = Modifier
                    .padding(start = spacing.spaceSmall)
                    .align(Alignment.CenterVertically),
                text = namingRule.priority.toString()
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = spacing.spaceMedium)
        ) {
            IconButton(
                onClick = { onEditClick(namingRule) }
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(
                onClick = { onDeleteClick(namingRule) }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}