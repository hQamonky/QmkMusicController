package com.qmk.music_controller.naming_rule_presentation.delete

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.qmk.music_controller.core_presentation.LocalSpacing
import com.qmk.music_controller.music_manager_domain.model.NamingRule
import com.qmk.music_controller.naming_rule_presentation.R

@Composable
fun DeleteNamingRuleScreen(
    modifier: Modifier = Modifier,
    namingRule: NamingRule,
    onConfirmClick: (namingRule: NamingRule) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = stringResource(id = R.string.delete_naming_rule),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.padding(spacing.spaceMedium),
            text = stringResource(id = R.string.confirm_delete_naming_rule)
        )
        Row(
            modifier = Modifier
                .padding(
                    start = spacing.spaceSmall,
                    end = spacing.spaceSmall
                )
                .fillMaxWidth(),
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
        Row {
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .weight(1f),
                onClick = { onCancelClick() }
            ) {
                Text(text = stringResource(
                    id = com.qmk.music_controller.core_presentation.R.string.cancel))
            }
            Button(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .weight(1f),
                onClick = { onConfirmClick(namingRule) }
            ) {
                Text(text = stringResource(
                    id = com.qmk.music_controller.core_presentation.R.string.confirm))
            }
        }
    }
}