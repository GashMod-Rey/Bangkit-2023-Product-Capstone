package umn.ac.id.myapplication.ui.chat.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import umn.ac.id.myapplication.ui.chat.model.User
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val _getUserViewModel = MutableLiveData<List<User>>()
    private val getUserViewModel: LiveData<List<User>> = _getUserViewModel
    fun addListUsers(listUser: List<User>) {
        _getUserViewModel.postValue(listUser)
    }

    fun getAllUser() = getUserViewModel
}