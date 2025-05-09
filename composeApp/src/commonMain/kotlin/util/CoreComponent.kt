package util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.local.datastore.app.AppPreferences
import data.local.datastore.app.AppPreferencesImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.plus
import ui.common.dataStorePreferences

interface CoreComponent : CoroutinesComponent {
    val appPreferences: AppPreferences
}

internal class CoreComponentImpl internal constructor() : CoreComponent,
    CoroutinesComponent by CoroutinesComponentImpl.create() {

    private val dataStore: DataStore<Preferences> = dataStorePreferences(
        corruptionHandler = null,
        coroutineScope = applicationScope + Dispatchers.IO,
        migrations = emptyList()
    )

    override val appPreferences: AppPreferences = AppPreferencesImpl(dataStore)
}