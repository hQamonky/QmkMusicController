package com.qmk.music_controller.setting_presentation.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qmk.music_controller.setting_presentation.display.DisplaySettingsScreen
import com.qmk.music_controller.setting_presentation.edit_server_info.EditServerInfoScreen
import com.qmk.music_controller.setting_presentation.edit_server_settings.EditServerSettingsScreen
import com.qmk.music_controller.setting_presentation.main.SettingsRoute.DISPLAY_SETTINGS
import com.qmk.music_controller.setting_presentation.main.SettingsRoute.EDIT_SERVER_INFO
import com.qmk.music_controller.setting_presentation.main.SettingsRoute.EDIT_SERVER_SETTINGS
import com.qmk.music_controller.setting_presentation.main.SettingsRoute.FACTORY_RESET
import com.qmk.music_controller.setting_presentation.main.SettingsRoute.UPDATE_YOUTUBE_DL

@Composable
fun SettingsScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DISPLAY_SETTINGS
    ) {
        composable(DISPLAY_SETTINGS) {
            DisplaySettingsScreen(
                onEditServerInfoClick = {
                    navController.navigate(EDIT_SERVER_INFO)
                },
                onEditServerSettingsClick = {
                    navController.navigate(EDIT_SERVER_SETTINGS)
                },
                onUpdateYoutubeDlClick = {
                    navController.navigate(UPDATE_YOUTUBE_DL)
                },
                onFactoryResetClick = {
                    navController.navigate(FACTORY_RESET)
                }
            )
        }
        composable(EDIT_SERVER_INFO) {
            EditServerInfoScreen(onNavigateUp = { navController.navigate(DISPLAY_SETTINGS) })
        }
        composable(EDIT_SERVER_SETTINGS) {
            EditServerSettingsScreen(onNavigateUp = { navController.navigate(DISPLAY_SETTINGS) })
        }
        composable(UPDATE_YOUTUBE_DL) {
            Text(text = "Update youtube-dl")
        }
        composable(FACTORY_RESET) {
            Text(text = "Factory reset")
        }
    }
}