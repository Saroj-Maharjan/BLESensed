package com.anchor.ugzeroharm.data.datasource.local.locale

import com.anchor.ugzeroharm.data.local.pref.PreferencesManager
import kotlinx.coroutines.flow.first

internal class LanguageLocalDataSourceImpl(
    private val preferencesManager: PreferencesManager,
) : LanguageLocalDataSource {
    override suspend fun getLanguageTag(): String? = preferencesManager.getString(KEY).first()?.takeIf { it.isNotEmpty() }

    override suspend fun setLanguageTag(tag: String?) {
        preferencesManager.putString(KEY, tag.orEmpty())
    }

    private companion object {
        const val KEY = "language_tag"
    }
}
