package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession

class ChatCompanyViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }

    fun getSession(): LiveData<UserSession> {
        return userPreferences.getSession().asLiveData()
    }

    val text: LiveData<String> = _text
}