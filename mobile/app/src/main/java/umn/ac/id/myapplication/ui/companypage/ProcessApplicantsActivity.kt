package umn.ac.id.myapplication.ui.companypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import io.jsonwebtoken.Jwts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityProcessApplicantsBinding
import umn.ac.id.myapplication.databinding.ActivitySignUpBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.data.ProcessResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.ProcessViewModel
import umn.ac.id.myapplication.ui.viewmodel.ProcessViewModelFactory

class ProcessApplicantsActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private lateinit var processViewModel: ProcessViewModel
    private var token = ""
    private lateinit var usernameA: String
    private lateinit var usernameC: String
    private lateinit var binding: ActivityProcessApplicantsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessApplicantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        usernameA = intent.getStringExtra("usernameA")?:""
        processViewModel = ViewModelProvider(this, ProcessViewModelFactory(pref))[ProcessViewModel::class.java]


        processViewModel.getSession().observe(this){
            if(it.isLogin){
                token = it.token
                usernameC = extractUsernameFromToken(token)

            }
        }

        binding.buttonProcess.setOnClickListener {
            processApplicant()
        }
    }

    private fun processApplicant(){
        val apiService = ApiClient.apiInstance

        val call = apiService.offerProcess(token, usernameA, usernameC)

        call.enqueue(object: Callback<ProcessResponse>{
            override fun onResponse(
                call: Call<ProcessResponse>,
                response: Response<ProcessResponse>
            ) {
                if(response.isSuccessful){
                    val processResponse = response.body()
                    Toast.makeText(this@ProcessApplicantsActivity, "Applicant processed successfully", Toast.LENGTH_SHORT).show()

                }
                else {
                    val errorMessage = response.errorBody()?.string()
                    Toast.makeText(this@ProcessApplicantsActivity, "Failed to process applicant: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProcessResponse>, t: Throwable) {
                Toast.makeText(this@ProcessApplicantsActivity, "Failed to process applicant: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun extractUsernameFromToken(token: String): String {
        try {
            val claims = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .body
            return claims.subject
        } catch (e: Exception){
            Log.e(TAG, "Failed to extract username from token: ${e.message}")
        }
        throw IllegalArgumentException("Failed to extract username from token")
    }

    companion object{
        private const val TAG = "ProcessApplicantActivity"
    }
}