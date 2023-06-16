package umn.ac.id.myapplication.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany.ProfileCompanyViewModel
import umn.ac.id.myapplication.ui.data.UserPreferences

class ProfileCompanyViewModelFactory(private val userPreferences: UserPreferences) :
    ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileCompanyViewModel::class.java)){
            return ProfileCompanyViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}