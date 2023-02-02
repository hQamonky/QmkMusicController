package com.qmk.music_controller.onboarding_presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qmk.music_controller.onboarding_presentation.OnboardingRoute.MAIN_SCREEN
import com.qmk.music_controller.onboarding_presentation.OnboardingRoute.SERVER_INFO
import com.qmk.music_controller.onboarding_presentation.server_info.ServerInfoScreen

@Composable
fun OnboardingNavigation(
    viewModel: OnboardingViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (viewModel.showInitialization) {
            SERVER_INFO
        } else {
            MAIN_SCREEN
        }
    ) {
        composable(SERVER_INFO) {
            ServerInfoScreen(
                modifier = Modifier.fillMaxSize(),
                onNextClick = {
                    navController.navigate(MAIN_SCREEN)
                }
            )
        }
        composable(MAIN_SCREEN) {
            content()
        }
    }

}