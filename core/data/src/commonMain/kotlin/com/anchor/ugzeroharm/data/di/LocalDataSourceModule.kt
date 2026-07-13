package com.anchor.ugzeroharm.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.anchor.ugzeroharm.data.datasource.local.locale.LanguageLocalDataSource
import com.anchor.ugzeroharm.data.datasource.local.locale.LanguageLocalDataSourceImpl
import com.anchor.ugzeroharm.data.datasource.local.theme.ThemeLocalDataSource
import com.anchor.ugzeroharm.data.datasource.local.theme.ThemeLocalDataSourceImpl
import com.anchor.ugzeroharm.data.datasource.local.token.TokenLocalDataSource
import com.anchor.ugzeroharm.data.datasource.local.token.TokenLocalDataSourceImpl
import com.anchor.ugzeroharm.data.local.pref.PreferencesManager
import com.anchor.ugzeroharm.data.repository.ble.BleAdvertiserRepository
import com.anchor.ugzeroharm.data.repository.ble.BleAdvertiserRepositoryImpl
import com.anchor.ugzeroharm.data.repository.locale.LanguageRepository
import com.anchor.ugzeroharm.data.repository.locale.LanguageRepositoryImpl
import com.anchor.ugzeroharm.data.repository.theme.ThemeRepository
import com.anchor.ugzeroharm.data.repository.theme.ThemeRepositoryImpl
import com.anchor.ugzeroharm.data.repository.token.TokenRepository
import com.anchor.ugzeroharm.data.repository.token.TokenRepositoryImpl
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.bind
import org.koin.dsl.module

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"

object DataStorePathStringQualifier : Qualifier {
    override val value: QualifierValue
        get() = DataStorePathStringQualifier::class.simpleName.toString()
}

internal expect val platformLocalDataSourceModule: Module

internal val localDataSourceModule =
    module {
        single { PreferencesManager(get()) }

        single<DataStore<Preferences>> {
            PreferenceDataStoreFactory.createWithPath(
                produceFile = { get<String>(qualifier = DataStorePathStringQualifier).toPath() },
            )
        }

        // Token
        singleOf(::TokenLocalDataSourceImpl).bind<TokenLocalDataSource>()
        singleOf(::TokenRepositoryImpl).bind<TokenRepository>()

        // Theme
        singleOf(::ThemeLocalDataSourceImpl).bind<ThemeLocalDataSource>()
        singleOf(::ThemeRepositoryImpl).bind<ThemeRepository>()

        // Locale
        singleOf(::LanguageLocalDataSourceImpl).bind<LanguageLocalDataSource>()
        singleOf(::LanguageRepositoryImpl).bind<LanguageRepository>()

        // BLE
        singleOf(::BleAdvertiserRepositoryImpl).bind<BleAdvertiserRepository>()
    }

/*
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "X-db",
        ).build()
    }*/
