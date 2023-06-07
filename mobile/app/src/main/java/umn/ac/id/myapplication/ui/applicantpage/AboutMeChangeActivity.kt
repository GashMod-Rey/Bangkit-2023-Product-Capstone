package umn.ac.id.myapplication.ui.applicantpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityAboutMeChangeBinding
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodelfactory.ProfileViewModelFactory

class AboutMeChangeActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private lateinit var binding: ActivityAboutMeChangeBinding
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, ProfileViewModelFactory(pref))[ProfileViewModel::class.java]

        viewModel.cvData.observe(this){
            cvData ->
            cvData?.let {
                binding.addName.setText(cvData.Name)
                binding.addDate.setText(cvData.YearOfBirth)
                binding.addDegree.setText(cvData.Degree)
                binding.addDesc.setText(cvData.Summary)
                binding.addEmail.setText(cvData.Email)
                binding.addEducationInstitution.setText(cvData.EducationInstitution)
                binding.addPhone.setText(cvData.MobilePhone)
                binding.addLanguage.setText(cvData.Language)
                binding.addSalaryMinimum.setText(cvData.SalaryMinimum.toString())
                binding.addSkills.setText(cvData.Skills)
                binding.addLocation.setText(cvData.Location)
            }
        }
        viewModel.getSession().observe(this){
            if(it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")
                viewModel.getCvData(token)

            }
        }
    }
}