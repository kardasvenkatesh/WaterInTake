package com.template.app.presentation.navigation

/**
 * Sealed class defining all app screens and routes.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    // TODO: Add your screens here
}
