package umn.ac.id.myapplication.ui.companypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityJobPreferencesBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.companypage.ui.home.HomeCompanyFragment
import umn.ac.id.myapplication.ui.data.FilterOptions
import umn.ac.id.myapplication.ui.data.FilterResponse
import umn.ac.id.myapplication.ui.data.UserDataResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory
import com.google.gson.Gson

class JobPreferencesActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private var token = ""
    private val filterViewModel by viewModels<MainViewModel>()
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityJobPreferencesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chipGroupSkills = binding.chipGroupSkills
        val chipGroupLanguage = binding.chipGroupLanguage

        val selectedSkills = mutableListOf<String>()
        val selectedLanguages = mutableListOf<String>()

        var tempSkills = mutableListOf<Int>()
        var tempLangs = mutableListOf<Int>()

        chipGroupSkills.setOnCheckedStateChangeListener { group, checkedId ->
            tempSkills = checkedId
        }

        chipGroupLanguage.setOnCheckedStateChangeListener { group, checkedId ->
            tempLangs = checkedId
        }

        binding.btnSave.setOnClickListener {
            for (id in tempSkills) {
                val chip = findViewById<Chip>(id)
                if (chip != null) {
                    val lang = chip.text.toString()
                    selectedLanguages.add(lang)
                }
            }

            for (id in tempLangs) {
                val chip = findViewById<Chip>(id)
                if(chip!= null){
                    val skill = chip.text.toString()
                    selectedSkills.add(skill)
                }
            }

            val ageMin = binding.addAgeMin.text.toString().toIntOrNull()
            val ageMax = binding.addAgeMax.text.toString().toIntOrNull()
            val salaryMin = binding.addSalaryMin.text.toString().toFloatOrNull()
            val salaryMax = binding.addSalaryMax.text.toString().toFloatOrNull()

            val ageFilter = listOfNotNull(ageMin, ageMax)
            val salaryFilter = listOfNotNull(salaryMin, salaryMax)
            val skillFilter = selectedSkills
            val langFilter = selectedLanguages
            val tolerance = binding.ageTolerance.text.toString().toInt()
            val tol = binding.salaryTolerance.text.toString().toFloat()
            val location = binding.addLocation.text.toString()

            sendFilter(ageFilter, salaryFilter, skillFilter, langFilter, tolerance, tol, location)
        }

    }

    private fun sendFilter(ageFilter: List<Int>, salaryFilter: List<Float>, skillFilter: List<String>, langFilter: List<String>, tolerance:Int, tol: Float, location: String){
        val pref = UserPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.getSession().observe(this){
            if (it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")
                mainViewModel.filterUsers(token, ageFilter, tolerance, skillFilter, langFilter, salaryFilter, tol, location)
                mainViewModel.filterResponse.observe(this){
                    when(it){
                        is Resource.Success ->{
                            val filteredUsers = it.data
                            val intent = Intent(this@JobPreferencesActivity, MainCompanyActivity::class.java)
                            val filteredUsersJSON = Gson().toJson(filteredUsers)
                            intent.putExtra("fil_user", filteredUsersJSON)
                            startActivity(intent)
                        }
                        is Resource.Error ->{
                            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }


}