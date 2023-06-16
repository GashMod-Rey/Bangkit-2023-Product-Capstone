package umn.ac.id.myapplication.ui.applicantpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityHistorySuccessBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.applicantpage.adapter.UserHistoryAdapter
import umn.ac.id.myapplication.ui.data.HistoryDataResponse
import umn.ac.id.myapplication.ui.data.HistoryResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.model.User
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory

class HistorySuccessActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private var token = ""
    private lateinit var userPreferences: UserPreferences
    private lateinit var binding: ActivityHistorySuccessBinding
    private lateinit var adapter: UserHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)

        val viewModel =
            ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]

        viewModel.getSession().observe(this) {
            if (it.isLogin) {
                token = it.token
                Log.e("TAG", "onCreate: $token")
                val client = ApiClient.apiInstance.getHistoryApplicant(token)
                client.enqueue(object : Callback<HistoryResponse> {
                    override fun onResponse(
                        call: Call<HistoryResponse>,
                        response: Response<HistoryResponse>
                    ) {
                        if (response.isSuccessful) {
                            val historyResponse = response.body()
                            val filteredList = historyResponse?.filter { it.Status == 1 }
                            val userList = filteredList?.map {
                                it.UsernameC
                            } ?: emptyList()

                            adapter = UserHistoryAdapter(
                                ArrayList(),
                                object : UserHistoryAdapter.OnClickItem {
                                    override fun onClick(historyData: HistoryDataResponse) {
                                        TODO("Not yet implemented")
                                    }
                                })
                            adapter.notifyDataSetChanged()
                            binding.successList.layoutManager =
                                LinearLayoutManager(this@HistorySuccessActivity)
                            binding.successList.adapter = adapter
                        } else {
                            val errorMessage = "Error: ${response.code()}"
                            Log.e("API Error", errorMessage)
                        }
                    }

                    override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                        val errorMessage = "Failed to connect: ${t.message}"
                        Log.e("API Failed", errorMessage)
                    }
                })
            }
        }
    }
}