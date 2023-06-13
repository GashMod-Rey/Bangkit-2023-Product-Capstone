package umn.ac.id.myapplication.ui.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getSession(): Flow<UserSession>{
        return dataStore.data.map {
            preferences ->
            UserSession(
                preferences[IS_LOGIN] ?: false,
                preferences[TOKEN]?: "",
                preferences[USERNAME]?: "",
            )
        }
    }

    suspend fun saveSession(userSession: UserSession){
        dataStore.edit {
            preferences ->
            preferences[IS_LOGIN] = userSession.isLogin
            preferences[TOKEN] = userSession.token
            preferences[USERNAME] = userSession.username
        }
    }

    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }

    companion object{
        private val IS_LOGIN = booleanPreferencesKey("is_login")
        private val TOKEN = stringPreferencesKey("token")
        private val USERNAME = stringPreferencesKey("username")

        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this){
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}