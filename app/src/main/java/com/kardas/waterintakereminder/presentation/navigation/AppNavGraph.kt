package com.kardas.waterintakereminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kardas.waterintakereminder.presentation.screens.home.HomeScreen
import com.kardas.waterintakereminder.presentation.screens.insights.InsightsScreen
import com.kardas.waterintakereminder.presentation.screens.logwater.LogWaterScreen
import com.kardas.waterintakereminder.presentation.screens.onboarding.OnboardingViewModel
import com.kardas.waterintakereminder.presentation.screens.onboarding.personal.PersonalInfoScreen
import com.kardas.waterintakereminder.presentation.screens.onboarding.schedule.ScheduleScreen
import com.kardas.waterintakereminder.presentation.screens.onboarding.welcome.WelcomeScreen
import com.kardas.waterintakereminder.presentation.screens.onboarding.workstyle.WorkStyleScreen
import com.kardas.waterintakereminder.presentation.screens.settings.SettingsScreen
import com.kardas.waterintakereminder.presentation.screens.splash.SplashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate("onboarding") {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        navigation(startDestination = Screen.Welcome.route, route = "onboarding") {
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onNavigateToPersonal = { navController.navigate(Screen.PersonalInfo.route) }
                )
            }
            composable(Screen.PersonalInfo.route) { entry ->
                val parentEntry = remember(entry) { navController.getBackStackEntry("onboarding") }
                val sharedViewModel: OnboardingViewModel = hiltViewModel(parentEntry)
                PersonalInfoScreen(
                    viewModel = sharedViewModel,
                    onNavigateToWorkStyle = { navController.navigate(Screen.WorkStyle.route) }
                )
            }
            composable(Screen.WorkStyle.route) { entry ->
                val parentEntry = remember(entry) { navController.getBackStackEntry("onboarding") }
                val sharedViewModel: OnboardingViewModel = hiltViewModel(parentEntry)
                WorkStyleScreen(
                    viewModel = sharedViewModel,
                    onNavigateToSchedule = { navController.navigate(Screen.Schedule.route) }
                )
            }
            composable(Screen.Schedule.route) { entry ->
                val parentEntry = remember(entry) { navController.getBackStackEntry("onboarding") }
                val sharedViewModel: OnboardingViewModel = hiltViewModel(parentEntry)
                ScheduleScreen(
                    viewModel = sharedViewModel,
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                )
            }
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToLogWater = { navController.navigate(Screen.LogWater.route) }
            )
        }
        
        composable(Screen.LogWater.route) {
            LogWaterScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Insights.route) {
            InsightsScreen()
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
