package com.anchor.ugzeroharm.permissionscreen.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.anchor.ugzeroharm.permissionscreen.presentation.ui.PermissionScreen
import com.anchor.ugzeroharm.permissionscreen.presentation.viewmodel.PermissionScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object PermissionRoute

fun NavGraphBuilder.permissionScreen(onAllPermissionsGranted: () -> Unit) {
    composable<PermissionRoute> {
        val viewModel: PermissionScreenViewModel = koinViewModel()
        PermissionScreen(
            viewModel = viewModel,
            onAllPermissionsGranted = onAllPermissionsGranted,
        )
    }
}
