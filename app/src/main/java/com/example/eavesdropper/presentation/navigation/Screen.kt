package com.example.eavesdropper.presentation.navigation

sealed class Screen(
    val route: String
) {

    object MainScreen: Screen(ROUTE_MAIN_SCREEN)
    object LogInScreen: Screen(ROUTE_LOG_IN_SCREEN)
    object RegistrationScreen: Screen(ROUTE_REGISTRATION_SCREEN)
    object SettingsScreen: Screen(ROUTE_SETTINGS_SCREEN)
    object ForgotPasswordScreen: Screen(FORGOT_PASSWORD_SCREEN)
    object List: Screen(ROUTE_LIST)
    object AboutAppScreen: Screen(ROUTE_ABOUT_APP_SCREEN)
    object AboutAccountScreen: Screen(ROUTE_ABOUT_ACCOUNT_SCREEN)
    object ListOfAsksScreen: Screen(ROUTE_LIST_OF_ASKS_SCREEN)

    private companion object {

        const val ROUTE_MAIN_SCREEN = "main_screen"
        const val ROUTE_LOG_IN_SCREEN = "log_in_screen"
        const val FORGOT_PASSWORD_SCREEN = "forgot_password_screen"
        const val ROUTE_LIST = "list"
        const val ROUTE_REGISTRATION_SCREEN = "registration_screen"
        const val ROUTE_SETTINGS_SCREEN = "settings_screen"
        const val ROUTE_ABOUT_APP_SCREEN = "about_app_screen"
        const val ROUTE_ABOUT_ACCOUNT_SCREEN = "about_account_screen"
        const val ROUTE_LIST_OF_ASKS_SCREEN = "list_of_asks_screen"
    }
}