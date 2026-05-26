package com.kardas.waterintakereminder.presentation.navigation

/**
 * Sealed class defining all app screens and routes.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("onboarding_welcome")
    object PersonalInfo : Screen("onboarding_personal")
    object WorkStyle : Screen("onboarding_workstyle")
    object Schedule : Screen("onboarding_schedule")
    object Home : Screen("home")
    object LogWater : Screen("log_water")
    object Insights : Screen("insights")
    object Settings : Screen("settings")
}
