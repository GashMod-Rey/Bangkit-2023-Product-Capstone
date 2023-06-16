package umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany

import android.util.Log
import androidx.lifecycle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.data.*
import umn.ac.id.myapplication.ui.utils.Resource

class ProfileCompanyViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _companyData = MutableLiveData<Resource<GetCompanyProfileResponse>?>()
    val companyData: LiveData<Resource<GetCompanyProfileResponse>?>  = _companyData

    private val _updateCompanyData = MutableLiveData<Resource<ProfileApplicantResponse?>>()
    val updateCompanyData: LiveData<Resource<ProfileApplicantResponse?>> = _updateCompanyData

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSession(): LiveData<UserSession> {
        return userPreferences.getSession().asLiveData()
    }


    fun getCompanyData(token: String) {

        _isLoading.value = true
        _isError.value = false

        val client = ApiClient.apiInstance.getProfileCompany(token)
        client.enqueue(object : Callback<GetCompanyProfileResponse> {
            override fun onResponse(
                call: Call<GetCompanyProfileResponse>,
                response: Response<GetCompanyProfileResponse>
            ) {
                if(response.isSuccessful){
                    val responseData = response.body()
                    responseData?.let{
                        _companyData.value = Resource.Success(it)
                        Log.d(TAG, "onResponse: $responseData")
                    }

                }
                else {
                    val errorMessage = response.errorBody()?.string()
                    _companyData.value = Resource.Error(errorMessage)
                    Log.e(TAG, "onResponse error: ${response.message()}")
                }
                _isLoading.value = false


            }

            override fun onFailure(call: Call<GetCompanyProfileResponse>, t: Throwable) {
                _error.value = "Failed to fetch CV data: ${t.message}"
                Log.e(TAG, "onFailure: ${t.message}")
                _isError.value = true
            }

        })
    }

    fun updateProfileCompany(
        token: String,
        Name: String,
        Summary: String,
        Location: String,
        Employee: Int,
    ){
        val client = ApiClient.apiInstance.setProfileCompany(
            token, Name, Summary, Location, Employee
        )

        client.enqueue(object : Callback<ProfileApplicantResponse> {
            override fun onResponse(
                call: Call<ProfileApplicantResponse>,
                response: Response<ProfileApplicantResponse>
            ) {
                if(response.isSuccessful){
                    _updateCompanyData.value = Resource.Success(response.body())
                    Log.d(TAG, "Profile updated successfully")
                }
                else {
                    val errorMessage = response.errorBody()?.string()
                    _updateCompanyData.value = Resource.Error(errorMessage)
                    Log.e(TAG, "Failed to update ")
                }
            }

            override fun onFailure(call: Call<ProfileApplicantResponse>, t: Throwable) {
                Log.e(TAG, "Failed to update profile: ${t.message}")
            }
        })

    }


    companion object {
        private const val TAG = "ProfileViewModel"
    }

}

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