package umn.ac.id.myapplication.ui.viewmodel

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.data.UploadCVResponse
import umn.ac.id.myapplication.ui.utils.Resource
import java.io.File

class UploadCvViewModel : ViewModel() {
    private val _pdf = MutableLiveData<Resource<UploadCVResponse>>()
    val pdf : LiveData<Resource<UploadCVResponse>> = _pdf
    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean>
        get() = _uploadStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun uploadFile(pdfUri: MultipartBody.Part){

        val client = ApiClient.apiInstance.uploadCV(pdfUri)
        client.enqueue(object: Callback<UploadCVResponse>{
            override fun onResponse(
                call: Call<UploadCVResponse>,
                response: Response<UploadCVResponse>
            ) {
                if (response.isSuccessful){
                    _pdf.value = response.body()?.let {
                        Resource.Success(it)
                    }
                    Log.d(TAG, "onResponse: ${_pdf.value}")
                }
                else {
                    _pdf.value = Resource.Error(response.message())
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UploadCVResponse>, t: Throwable) {
                _pdf.value = Resource.Error("${t.message}")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }
    companion object{
        private const val TAG = "UploadCvViewModel"
    }

}