package umn.ac.id.myapplication.ui.applicantpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityCompanyProfileBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.data.OfferApplicantResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory

class CompanyProfileActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private lateinit var binding: ActivityCompanyProfileBinding
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, MainViewModelFactory(pref))[ProfileViewModel::class.java]
        viewModel.getSession().observe(this){
            if(it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")

            }
        }
        binding.buttonAccept.setOnClickListener {
            val status = true
            sendStatus(true)
        }
        binding.buttonReject.setOnClickListener {
            val status = false
            sendStatus(false)
        }

    }

    private fun sendStatus(status: Boolean){
        val apiService = ApiClient.apiInstance
        val request = apiService.offerApplicant(token,status)

        request.enqueue(object: Callback<OfferApplicantResponse>{
            override fun onResponse(
                call: Call<OfferApplicantResponse>,
                response: Response<OfferApplicantResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@CompanyProfileActivity, "Status sent successfully", Toast.LENGTH_SHORT).show()
                }
                else{
                    val errorMessage = response.errorBody()?.string()
                    Toast.makeText(this@CompanyProfileActivity, "Failed to send status: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OfferApplicantResponse>, t: Throwable) {
                Toast.makeText(this@CompanyProfileActivity, "Failed to send status: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })


    }

    companion object{
        private var TAG = "CompanyProfileActivity"
    }
}