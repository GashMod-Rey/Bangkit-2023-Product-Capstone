package umn.ac.id.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Header
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.data.*
import umn.ac.id.myapplication.ui.model.User
import umn.ac.id.myapplication.ui.utils.Resource

class MainViewModel(private val userPreferences: UserPreferences) : ViewModel(){
    private val _filterResponse = MutableLiveData<Resource<List<UserDataResponse>>>()
    val filterResponse: LiveData<Resource<List<UserDataResponse>>> = _filterResponse

    fun filterUsers(token: String,
                            ageFilter: List<Int>,
                            tolerance: Int,
                            skillLang: List<String>,
                            langFilter: List<String>,
                            salaryFilter: List<Float>,
                            tol: Float,
                            location: String){
                _filterResponse.value
            val client = ApiClient.apiInstance.filterUsers(token, ageFilter, tolerance, skillLang, langFilter, salaryFilter, tol, location)
                client.enqueue(object: Callback<FilterResponse> {
                    override fun onResponse(
                        call: Call<FilterResponse>,
                        response: Response<FilterResponse>
                    ) {
                        if (response.isSuccessful){
                            _filterResponse.value = response.body()?.let { Resource.Success(it) }
                            Log.d("JobPreferencesActivity", "onResponse: ${_filterResponse.value}")

                        } else {
                            val errorBody = response.errorBody()?.string()
                            val errorMessage = errorBody?.let {
                                JSONObject(it).getString("message")
                            }
                            _filterResponse.value = Resource.Error(errorMessage)
                            Log.e("JobPreferencesActivity", "onResponse: $errorMessage")
                            }
                        }
                    override fun onFailure(call: Call<FilterResponse>, t: Throwable) {
                        _filterResponse.value = Resource.Error("${t.message}")
                        Log.e("JobPreferencesActivity", "onFailure: ${t.message}")
                    }


                })

    }
    fun getSession(): LiveData<UserSession> {
        return userPreferences.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }

}