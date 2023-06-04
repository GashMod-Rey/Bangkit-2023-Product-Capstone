package umn.ac.id.myapplication.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.LoginViewModel

class LoginViewModelFactory(private val userPreferences: UserPreferences):
ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}
