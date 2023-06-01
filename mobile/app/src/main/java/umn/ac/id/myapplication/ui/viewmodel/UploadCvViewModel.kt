package umn.ac.id.myapplication.ui.viewmodel

import android.net.Uri
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
import umn.ac.id.myapplication.ui.api.ApiClient
import java.io.File

class UploadCvViewModel : ViewModel() {
    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean>
        get() = _uploadStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun uploadFile(pdfUri: Uri){
        val file = File(pdfUri.path)
        val requestBody = RequestBody.create("application/pdf".toMediaTypeOrNull(), file)
        val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.apiInstance.uploadCV(multipartBody)
                if (response.isSuccessful) {
                    val message = response.message()
                    _uploadStatus.postValue(true)

                } else {
                    val errorBody = response.errorBody()?.string()?: "Unknown error"
                    _uploadStatus.postValue(false)
                    _errorMessage.postValue(errorBody)

                }
            } catch (t: Throwable) {
                t.printStackTrace()
                _uploadStatus.postValue(false)
                _errorMessage.postValue("Upload Failed, kesalahan jaringan")

            }
        }

    }

}