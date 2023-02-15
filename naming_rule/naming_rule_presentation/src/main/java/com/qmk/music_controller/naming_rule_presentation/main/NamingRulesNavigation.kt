package com.qmk.music_controller.naming_rule_presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qmk.music_controller.core_presentation.UiEvent
import com.qmk.music_controller.core_presentation.component.ErrorScreen
import com.qmk.music_controller.core_presentation.component.LoadingScreen
import com.qmk.music_controller.naming_rule_presentation.add.AddNamingRuleScreen
import com.qmk.music_controller.naming_rule_presentation.delete.DeleteNamingRuleScreen
import com.qmk.music_controller.naming_rule_presentation.edit.EditNamingRuleScreen
import com.qmk.music_controller.naming_rule_presentation.list.LoadingState
import com.qmk.music_controller.naming_rule_presentation.list.NamingRulesScreen
import com.qmk.music_controller.naming_rule_presentation.main.NamingRuleRoute.ADD_NAMING_RULE
import com.qmk.music_controller.naming_rule_presentation.main.NamingRuleRoute.DELETE_NAMING_RULE
import com.qmk.music_controller.naming_rule_presentation.main.NamingRuleRoute.EDIT_NAMING_RULE
import com.qmk.music_controller.naming_rule_presentation.main.NamingRuleRoute.LIST
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamingRulesNavigation(
    viewModel: NamingRuleViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(event.message.asString(context))
                    }
                }
                is UiEvent.NavigateTo -> {
                    navController.navigate(event.route)
                }
                is UiEvent.NavigateUp -> navController.navigateUp()
                else -> Unit
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        when(state.loadingState) {
            LoadingState.STANDBY -> NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = LIST
            ) {
                composable(LIST) {
                    NamingRulesScreen(
                        onAddNamingRuleClick = { navController.navigate(ADD_NAMING_RULE) },
                        onEditClick = { viewModel.getNamingRuleForEdit(it) },
                        onDeleteClick = { viewModel.getNamingRuleForDelete(it) }
                    )
                }
                composable(ADD_NAMING_RULE) {
                    AddNamingRuleScreen {
                        it?.let {
                            scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                        navController.navigate(LIST)
                    }
                }
                composable(EDIT_NAMING_RULE) {
                    state.processingNamingRule?.let { namingRule ->
                        EditNamingRuleScreen(
                            replaceValue = state.editNamingRuleState.replace,
                            replaceByValue = state.editNamingRuleState.replaceBy,
                            priorityValue = state.editNamingRuleState.priority,
                            onReplaceFiledValueChange = { viewModel.onEditReplaceEnter(it) },
                            onReplaceByFiledValueChange = { viewModel.onEditReplaceByEnter(it) },
                            onPriorityFiledValueChange = { viewModel.onEditPriorityEnter(it) },
                            onConfirmClick = {
                                viewModel.editNamingRule(namingRule)
                            },
                            onCancelClick = { navController.navigate(LIST) }
                        )
                    }
                }
                composable(DELETE_NAMING_RULE) {
                    state.processingNamingRule?.let { namingRule ->
                        DeleteNamingRuleScreen(
                            namingRule = namingRule,
                            onConfirmClick = {
                                viewModel.deleteNamingRule(namingRule)
                            },
                            onCancelClick = { navController.navigate(LIST) }
                        )
                    }
                }
            }
            LoadingState.LOADING -> LoadingScreen(
                modifier = Modifier.fillMaxSize(),
                text = state.customMessage?.asString(context)
            )
            LoadingState.ERROR -> ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                text = state.customMessage?.asString(context)
            )
        }
    }
}