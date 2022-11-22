package com.qmk.musiccontroller.presentation.settings

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qmk.musiccontroller.R
import com.qmk.musiccontroller.domain.controller.SettingsManager
import com.qmk.musiccontroller.presentation.settings.SettingsNavigationDestinations
.EDIT_SETTINGS_ROUTE
import com.qmk.musiccontroller.presentation.settings.SettingsNavigationDestinations
.SETTINGS_DETAILS_ROUTE

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

object SettingsNavigationDestinations {
    const val SETTINGS_DETAILS_ROUTE = "settings-details"
    const val EDIT_SETTINGS_ROUTE = "edit-settings"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = SettingsViewModel(
        SettingsManager(LocalContext.current.dataStore))
) {
    val navController = rememberNavController()
    val smallPadding = dimensionResource(R.dimen.padding_large)
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    NavHost(
        navController = navController,
        startDestination = SETTINGS_DETAILS_ROUTE
    ) {
        composable(SETTINGS_DETAILS_ROUTE) {
            Scaffold(topBar = {
                    Text(modifier = Modifier.padding(mediumPadding),
                        style = MaterialTheme.typography.titleLarge,
                        text = "Settings")
            }) {
                Column {
                    SettingsDetailsScreen(viewModel)
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(smallPadding),
                        onClick = { navController.navigate(EDIT_SETTINGS_ROUTE) }
                    ) {
                        Text(text = "Edit")
                    }
                }
            }
        }
        composable(EDIT_SETTINGS_ROUTE) {
            Scaffold(topBar = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.navigate(SETTINGS_DETAILS_ROUTE) }) {
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_back_24),
                            contentDescription = "Back")}
                    Text(modifier = Modifier.padding(mediumPadding),
                        style = MaterialTheme.typography.titleLarge,
                        text = "Edit Settings")
                }
            }) {
                EditSettingsScreen(viewModel)
            }
        }
    }
}

@Composable
fun SettingsDetailsScreen(
    viewModel: SettingsViewModel = SettingsViewModel(
        SettingsManager(LocalContext.current.dataStore))
) {
    val state = viewModel.serverConfigurationFlow.collectAsState()
    val smallPadding = dimensionResource(R.dimen.padding_small)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(smallPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.padding(smallPadding)) {
            Text(text = "Server URL")
            Text(text = "Download Occurrence")
            Text(text = "Music Directory")
            Text(text = "Auto-download")
        }
        Column(Modifier.padding(smallPadding)) {
            Text(text = state.value?.url ?: "")
            Text(text = (state.value?.downloadOccurrence ?: "").toString())
            Text(text = state.value?.musicFolder ?: "")
            Text(text = (state.value?.autoDownload ?: "").toString())
        }
    }
}

@Composable
fun EditSettingsScreen(
    viewModel: SettingsViewModel = SettingsViewModel(
        SettingsManager(LocalContext.current.dataStore))
) {
    Text(text = "Settings Screen")
}

