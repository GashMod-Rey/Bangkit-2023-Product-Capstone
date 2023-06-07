package umn.ac.id.myapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession

class MainViewModel(private val userPreferences: UserPreferences) : ViewModel(){

    fun getSession(): LiveData<UserSession> {
        return userPreferences.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }

}