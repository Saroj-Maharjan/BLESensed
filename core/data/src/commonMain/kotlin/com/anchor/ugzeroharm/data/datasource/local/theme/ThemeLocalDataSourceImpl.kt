package com.anchor.ugzeroharm.data.datasource.local.theme

import com.anchor.ugzeroharm.data.local.pref.PreferencesManager
import com.anchor.ugzeroharm.data.repository.theme.AppThemeMode
import kotlinx.coroutines.flow.first

internal class ThemeLocalDataSourceImpl(
    private val preferencesManager: PreferencesManager,
) : ThemeLocalDataSource {
    override suspend fun getThemeMode(): AppThemeMode? =
        preferencesManager.getString(KEY).first()?.let { name ->
            AppThemeMode.entries.firstOrNull { it.name == name }
        }

    override suspend fun setThemeMode(mode: AppThemeMode) {
        preferencesManager.putString(KEY, mode.name)
    }

    private companion object {
        const val KEY = "theme_mode"
    }
}
