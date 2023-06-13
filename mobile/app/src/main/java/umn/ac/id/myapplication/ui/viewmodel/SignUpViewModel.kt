package umn.ac.id.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.socket.client.Socket
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.chat.model.User
import umn.ac.id.myapplication.ui.chat.other.SING_UP
import umn.ac.id.myapplication.ui.data.SignUpResponse
import umn.ac.id.myapplication.ui.utils.Resource

class SignUpViewModel: ViewModel(){
    private val _signup = MutableLiveData<Resource<SignUpResponse>>()
    val signup: LiveData<Resource<SignUpResponse>> = _signup

    fun postRegister(username: String, password: String, socket: Socket){
        val client = ApiClient.apiInstance.postSignUp(username, password)
        Log.d(TAG, "postLogin: $client")
        client.enqueue(object : Callback<SignUpResponse>{
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if(response.isSuccessful){
                    _signup.value = response.body()?.let{
                        Resource.Success(it)
                    }

                    var userID = JSONObject()
                    userID.put(User.USERNAME, username)
                    userID.put(User.PASSWORD, password)

                    socket!!.emit(SING_UP, username, userID)

                    Log.d(TAG, "onResponse: ${_signup.value}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        JSONObject(it).getString("message")
                    }
                    _signup.value = Resource.Error(errorMessage)
                    Log.e(TAG, "onResponse: $errorMessage")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                _signup.value = Resource.Error("${t.message}")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "SignUpViewModel"
    }

}