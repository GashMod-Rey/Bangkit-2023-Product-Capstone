package umn.ac.id.myapplication.ui.applicantpage.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.data.ProfileApplicantResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession
import umn.ac.id.myapplication.ui.utils.Resource

class ProfileViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _cvData = MutableLiveData<ProfileApplicantResponse>()
    val cvData: LiveData<ProfileApplicantResponse> get() = _cvData

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSession(): LiveData<UserSession> {

        return userPreferences.getSession().asLiveData()
    }

    var errorMessage: String = ""
        private set

    fun getCvData(token: String) {

        _isLoading.value = true
        _isError.value = false

        val client = ApiClient.apiInstance.getProfileApplicant(token)
        client.enqueue(object : Callback<ProfileApplicantResponse> {
            override fun onResponse(
                call: Call<ProfileApplicantResponse>,
                response: Response<ProfileApplicantResponse>
            ) {
                if(response.isSuccessful){
                    _cvData.value = response.body()
                    Log.d(TAG, "onResponse: ${_cvData.value}")
                }
                else {
                    _cvData.value = null
                    Log.e(TAG, "onResponse error: ${response.message()}")
                }
                _isLoading.value = false


            }

            override fun onFailure(call: Call<ProfileApplicantResponse>, t: Throwable) {
                _error.value = "Failed to fetch CV data: ${t.message}"
                Log.e(TAG, "onFailure: ${t.message}")
                _isError.value = true
            }

        })
    }


    companion object {
        private const val TAG = "ProfileViewModel"
    }


}