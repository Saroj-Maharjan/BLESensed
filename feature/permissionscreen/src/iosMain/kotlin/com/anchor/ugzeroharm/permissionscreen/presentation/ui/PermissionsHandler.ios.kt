package com.anchor.ugzeroharm.permissionscreen.presentation.ui

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Composable
actual fun handlePermissions(onPermissionsGranted: () -> Unit): () -> Unit =
    {
        onPermissionsGranted()
    }

@Composable
actual fun rememberSettingsLauncher(type: SettingsType): () -> Unit =
    {
        val url = NSURL(string = UIApplicationOpenSettingsURLString)
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }
    }
