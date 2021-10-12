package com.example.todo_compose.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.util.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = Constant.PREFERENCES_NAME)


@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context) {
    private object PreferencesKey{
        val sortState = stringPreferencesKey(Constant.PREFERENCES_KEY)
    }

    val dataStore = context.dataStore

    suspend fun persistStateSort(priority: Priority){
        dataStore.edit { preferences ->
            preferences[PreferencesKey.sortState] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data.catch { exception ->
        if(exception is IOException){
            emit(
                emptyPreferences()
            )
        }else{
            throw exception
        }
    }
        .map { preferences ->
            val sortState = preferences[PreferencesKey.sortState] ?: Priority.NONE.name
            println("Sotred Srate 2 ${sortState}")
            sortState
        }
}