package com.anchor.ugzeroharm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.anchor.ugzeroharm.bledevicelist.presentation.navigation.bleDeviceListScreen
import com.anchor.ugzeroharm.bledevicelist.presentation.navigation.navigateToBleDeviceList
import com.anchor.ugzeroharm.data.repository.ble.BleAdvertiserRepository
import com.anchor.ugzeroharm.data.repository.token.TokenRepository
import com.anchor.ugzeroharm.designsystem.XNavHost
import com.anchor.ugzeroharm.login.presentation.navigation.LoginRoute
import com.anchor.ugzeroharm.login.presentation.navigation.loginScreen
import com.anchor.ugzeroharm.permission.PermissionManager
import com.anchor.ugzeroharm.permissionscreen.presentation.navigation.PermissionRoute
import com.anchor.ugzeroharm.permissionscreen.presentation.navigation.permissionScreen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

/**
 * Main app navigation host.
 * Routes to PermissionRoute as the start destination.
 * Dynamically redirects to PermissionRoute if permissions or service states change.
 */
@Composable
fun BaseAppNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val permissionManager = koinInject<PermissionManager>()
    val btEnabled by permissionManager.isBluetoothEnabled.collectAsStateWithLifecycle()
    val locEnabled by permissionManager.isLocationEnabled.collectAsStateWithLifecycle()

    val tokenRepository = koinInject<TokenRepository>()
    val bleAdvertiserRepository = koinInject<BleAdvertiserRepository>()
    val scope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current

    // Trigger permission manager refresh when the app returns to the foreground
    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    permissionManager.refresh()
                }
            }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val hasPermissions =
        permissionManager.hasBluetoothPermission() &&
            permissionManager.hasLocationPermission() &&
            btEnabled &&
            locEnabled

    // Reactive redirection if permissions or service states change
    LaunchedEffect(hasPermissions, navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route
        val isAtPermissionScreen = currentRoute?.contains("PermissionRoute") == true

        if (!hasPermissions && !isAtPermissionScreen) {
            navController.navigate(PermissionRoute) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Auto-start or stop BLE advertisement based on permission and token existence
    LaunchedEffect(hasPermissions) {
        if (hasPermissions) {
            val deviceId = tokenRepository.getAccessToken()
            if (deviceId != null) {
                bleAdvertiserRepository.startAdvertising(deviceId)
            }
        } else {
            bleAdvertiserRepository.stopAdvertising()
        }
    }

    XNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PermissionRoute,
    ) {
        permissionScreen {
            navController.navigate(LoginRoute) {
                popUpTo(PermissionRoute) { inclusive = true }
            }
        }
        loginScreen { deviceId ->
            scope.launch {
                tokenRepository.saveAccessToken(deviceId)
                bleAdvertiserRepository.startAdvertising(deviceId)
            }
            navController.navigateToBleDeviceList()
        }
        bleDeviceListScreen {
            // Handle settings click if needed
        }
    }
}
