package com.anchor.ugzeroharm.bledevicelist.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.anchor.ugzeroharm.bledevicelist.presentation.ui.BleDeviceListRoute
import com.anchor.ugzeroharm.bledevicelist.presentation.viewmodel.BleDeviceListViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object BleDeviceListRoute

fun NavController.navigateToBleDeviceList() {
    navigate(BleDeviceListRoute) {
        popUpTo(0) { inclusive = true }
    }
}

fun NavGraphBuilder.bleDeviceListScreen(onSettingsClick: () -> Unit) {
    composable<BleDeviceListRoute> {
        val viewModel: BleDeviceListViewModel = koinViewModel()
        BleDeviceListRoute(
            viewModel = viewModel,
            onSettingsClick = onSettingsClick,
        )
    }
}
