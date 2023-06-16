package umn.ac.id.myapplication.ui.companypage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityHiringProcessCompanyBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.applicantpage.CompanyProfileActivity
import umn.ac.id.myapplication.ui.data.OfferApplicantResponse
import umn.ac.id.myapplication.ui.data.StatusResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory

class HiringProcessCompany : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private lateinit var binding: ActivityHiringProcessCompanyBinding
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiringProcessCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]
        viewModel.getSession().observe(this){
            if(it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")

                binding.buttonAccept.setOnClickListener {
                    val offer = true
                    sendStatus(token, offer)
                }

                binding.buttonReject.setOnClickListener {
                    val offer = false
                    sendStatus(token, offer)
                }
            }
        }


    }
    private fun sendStatus(token: String, offer: Boolean){
        val apiService = ApiClient.apiInstance
        val request = apiService.status(token,offer)

        request.enqueue(object: Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@HiringProcessCompany, "Offer sent successfully", Toast.LENGTH_SHORT).show()
                }
                else{
                    val errorMessage = response.errorBody()?.string()
                    Toast.makeText(this@HiringProcessCompany, "Failed to send offer: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Toast.makeText(this@HiringProcessCompany, "Failed to send status: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })


    }

    companion object{
        private var TAG = "HiringProcessCompanyActivity"
    }
}