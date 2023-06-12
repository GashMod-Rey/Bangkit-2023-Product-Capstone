package umn.ac.id.myapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession

class ProcessViewModel(private val userPreferences: UserPreferences): ViewModel() {

    fun getSession(): LiveData<UserSession> {
        return userPreferences.getSession().asLiveData()
    }


}

class ProcessViewModelFactory(private val userPreferences: UserPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(ProcessViewModel::class.java)){
            return ProcessViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}