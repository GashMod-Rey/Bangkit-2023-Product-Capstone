package umn.ac.id.myapplication.ui.applicantpage.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Header
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.data.GetProfileApplicantResponse
import umn.ac.id.myapplication.ui.data.ProfileApplicantResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession
import umn.ac.id.myapplication.ui.utils.Resource

class ProfileViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _cvData = MutableLiveData<Resource<GetProfileApplicantResponse?>>()
    val cvData: LiveData<Resource<GetProfileApplicantResponse?>>  = _cvData

    private val _updateCvData = MutableLiveData<Resource<ProfileApplicantResponse?>>()
    val updateCvData: LiveData<Resource<ProfileApplicantResponse?>> = _updateCvData

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSession(): LiveData<UserSession> {

        return userPreferences.getSession().asLiveData()
    }


    fun getCvData(token: String) {

        _isLoading.value = true
        _isError.value = false

        val client = ApiClient.apiInstance.getProfileApplicant(token)
        client.enqueue(object : Callback<GetProfileApplicantResponse> {
            override fun onResponse(
                call: Call<GetProfileApplicantResponse>,
                response: Response<GetProfileApplicantResponse>
            ) {
                if(response.isSuccessful){
                    _cvData.value = Resource.Success(response.body())
                    Log.d(TAG, "onResponse: ${_cvData.value}")
                }
                else {
                    val errorMessage = response.errorBody()?.string()
                    _cvData.value = Resource.Error(errorMessage)
                    Log.e(TAG, "onResponse error: ${response.message()}")
                }
                _isLoading.value = false


            }

            override fun onFailure(call: Call<GetProfileApplicantResponse>, t: Throwable) {
                _error.value = "Failed to fetch CV data: ${t.message}"
                Log.e(TAG, "onFailure: ${t.message}")
                _isError.value = true
            }

        })
    }

    fun updateProfile(
        token: String,
        name: String,
        date: String,
        degree: String,
        desc: String,
        email: String,
        educationInstitution: String,
        phone: String,
        language: String,
        salaryMinimum: Int,
        skills: String,
        location: String
    ){
        val client = ApiClient.apiInstance.setProfileApplicant(
            token, name, date, email, language, desc,
            educationInstitution, skills, salaryMinimum,
            location, degree, phone
        )

        client.enqueue(object : Callback<ProfileApplicantResponse>{
            override fun onResponse(
                call: Call<ProfileApplicantResponse>,
                response: Response<ProfileApplicantResponse>
            ) {
                if(response.isSuccessful){
                    _updateCvData.value = Resource.Success(response.body())
                    Log.d(TAG, "Profile updated successfully")
                }
                else {
                    val errorMessage = response.errorBody()?.string()
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