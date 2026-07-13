package com.anchor.ugzeroharm.login.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.anchor.ugzeroharm.login.presentation.ui.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

fun NavGraphBuilder.loginScreen(onLoginSuccess: (String) -> Unit) {
    composable<LoginRoute> {
        LoginScreen(onLoginSuccess = onLoginSuccess)
    }
}
