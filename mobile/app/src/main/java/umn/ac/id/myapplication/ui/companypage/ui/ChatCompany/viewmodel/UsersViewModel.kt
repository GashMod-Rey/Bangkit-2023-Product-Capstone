package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.User

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val _getUserViewModel = MutableLiveData<List<User>>()
    private val getUserViewModel: LiveData<List<User>> = _getUserViewModel
    fun addListUsers(listUser: List<User>) {
        _getUserViewModel.postValue(listUser)
    }

    fun getAllUser() = getUserViewModel
}