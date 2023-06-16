package umn.ac.id.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.data.LoginResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession
import umn.ac.id.myapplication.ui.model.User
import umn.ac.id.myapplication.ui.utils.Resource

class LoginViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> = _login

    fun saveSession(userSession: UserSession){
        viewModelScope.launch {
            userPreferences.saveSession(userSession)
        }
    }

    fun postLogin(username: String, password: String){
        val client = ApiClient.apiInstance.postLogin(username, password)
        Log.d(TAG, "postLogin: $client")
        client.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Log.d(TAG, "response: $response")
                if (response.isSuccessful){
                    _login.value = response.body()?.let{
                        Resource.Success(it)
                    }

                    Log.d(TAG, "onResponse: ${_login.value}")
                }
                else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        JSONObject(it).getString("message")
                    }
                    _login.value = Resource.Error(errorMessage)
                    Log.e(TAG, "onResponse: $errorMessage")
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                _login.value = Resource.Error("${t.message}")
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun postLoginCompany(username: String, password: String){
        val client = ApiClient.apiInstance.postLoginCompany(username, password)
        Log.d(TAG, "postLoginCompany: $client")
        client.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful){
                    _login.value = response.body()?.let{
                        Resource.Success(it)
                    }

                    Log.d(TAG, "onResponse: ${_login.value}")}
                else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        JSONObject(it).getString("message")
                    }
                    _login.value = Resource.Error(errorMessage)
                    Log.e(TAG, "onResponse: $errorMessage")
                }
            }
            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                _login.value = Resource.Error("${t.message}")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
//    fun postLoginCompany(username: String, password: String, socket: Socket, configUser: ConfigUser){
//        val client = ApiClient.apiInstance.postLoginCompany(username, password)
//        Log.d(TAG, "postLoginCompany: $client")
//        client.enqueue(object: Callback<LoginResponse>{
//            override fun onResponse(
//                call: retrofit2.Call<LoginResponse>,
//                response: Response<LoginResponse>
//            ) {
//                if (response.isSuccessful){
//                    _login.value = response.body()?.let{
//                        Resource.Success(it)
//                    }
//
//                    var userID = JSONObject()
//                    userID.put(User.USERNAME, username)
//                    userID.put(User.TOKEN, _login.value?.data?.token)
//                    socket!!.emit(SING_IN, username, userID)
//                    configUser!!.getEditor()!!.apply {
//                        putString(DATA_USER_NAME, username)
//                        putBoolean(IS_LOGIN, true)
//                        apply()
//                    }
//
//                    Emitter.Listener {
//                        socket!!.emit(GET_ALL_USER, true)
//                        socket!!.emit(UPDATE_DATA, JSONObject().apply {
//                            put(User.USERNAME, username)
//                            put(User.IS_ONLINE, true)
//                        })
//                    }
//
//                    Log.d(TAG, "onResponse: ${_login.value}")}
//                else {
//                    val errorBody = response.errorBody()?.string()
//                    val errorMessage = errorBody?.let {
//                        JSONObject(it).getString("message")
//                    }
//                    _login.value = Resource.Error(errorMessage)
//                    Log.e(TAG, "onResponse: $errorMessage")
//                }
//            }
//            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
//                _login.value = Resource.Error("${t.message}")
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    companion object{
        private const val TAG = "LoginViewModel"
    }
}