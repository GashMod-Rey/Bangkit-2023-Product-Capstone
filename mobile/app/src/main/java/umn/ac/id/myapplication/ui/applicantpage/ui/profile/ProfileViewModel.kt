package umn.ac.id.myapplication.ui.applicantpage.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.data.ProfileApplicantResponse

class ProfileViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text

    private val _cvData = MutableLiveData<ProfileApplicantResponse>()
    val cvData: LiveData<ProfileApplicantResponse> get() = _cvData

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()


    var errorMessage: String = ""
        private set

    fun getCvData(cv: String) {

        _isLoading.value = true
        _isError.value = false

        val client = ApiClient.apiInstance

        // Send API request using Retrofit
        client.getCV().enqueue(object : Callback<ProfileApplicantResponse> {

            override fun onResponse(
                call: Call<ProfileApplicantResponse>,
                response: Response<ProfileApplicantResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                _isLoading.value = false
                _cvData.postValue(responseBody)
            }

            override fun onFailure(call: Call<ProfileApplicantResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }

        })
    }

    private fun onError(inputMessage: String?) {

        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }


}