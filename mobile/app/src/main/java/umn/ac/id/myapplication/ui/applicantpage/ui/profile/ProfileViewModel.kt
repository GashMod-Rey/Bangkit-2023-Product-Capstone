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

    private val _cvData = MutableLiveData<Resource<ProfileApplicantResponse>>()
    val cvData: LiveData<Resource<ProfileApplicantResponse>> get() = _cvData

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()

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
                    _cvData.value = response.body()?.let {
                        Resource.Success(it)
                    }
                    Log.d(TAG, "onResponse: ${_cvData.value}")
                }


            }

            override fun onFailure(call: Call<ProfileApplicantResponse>, t: Throwable) {
                _cvData.value = Resource.Error("${t.message}")
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


    companion object {
        private const val TAG = "ProfileViewModel"
    }


}