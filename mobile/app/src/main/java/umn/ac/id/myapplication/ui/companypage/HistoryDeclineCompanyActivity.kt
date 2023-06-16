package umn.ac.id.myapplication.ui.companypage

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
import umn.ac.id.myapplication.databinding.ActivityHistoryDeclineCompanyBinding
import umn.ac.id.myapplication.databinding.ActivityHistorySuccessCompanyBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.applicantpage.adapter.CompanyAccHistoryAdapter
import umn.ac.id.myapplication.ui.data.HistoryDataResponse
import umn.ac.id.myapplication.ui.data.HistoryResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory


class HistoryDeclineCompanyActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private var token = ""
    private lateinit var userPreferences: UserPreferences
    private lateinit var binding: ActivityHistoryDeclineCompanyBinding
    private lateinit var adapter: CompanyAccHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDeclineCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)

        val viewModel =
            ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]

        viewModel.getSession().observe(this) {
            if (it.isLogin) {
                token = it.token
                Log.e("TAG", "onCreate: $token")
                val client = ApiClient.apiInstance.getHistoryCompany(token)
                client.enqueue(object : Callback<HistoryResponse> {
                    override fun onResponse(
                        call: Call<HistoryResponse>,
                        response: Response<HistoryResponse>
                    ) {
                        if (response.isSuccessful) {
                            val historyResponse = response.body()
                            val filteredList = historyResponse?.filter { it.Status == 1 }
                            val userList = filteredList?.map {
                                it.UsernameA
                            } ?: emptyList()

                            adapter = CompanyAccHistoryAdapter(
                                ArrayList(),
                                object : CompanyAccHistoryAdapter.OnClickItem {
                                    override fun onClick(historyData: HistoryDataResponse) {
                                        TODO("Not yet implemented")
                                    }
                                })
                            adapter.notifyDataSetChanged()
                            binding.userRejectList.layoutManager =
                                LinearLayoutManager(this@HistoryDeclineCompanyActivity)
                            binding.userRejectList.adapter = adapter
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