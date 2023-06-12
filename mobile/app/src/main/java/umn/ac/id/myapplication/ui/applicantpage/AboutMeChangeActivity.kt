package umn.ac.id.myapplication.ui.applicantpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityAboutMeChangeBinding
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.utils.Resource
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

        viewModel.getSession().observe(this){
            if(it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")
                viewModel.getCvData(token)
                viewModel.cvData.observe(this) { resource ->
                    when(resource){
                        is Resource.Success -> {
                            val profileApplicant = resource.data
                            profileApplicant?.let { data ->
                                    binding.apply{
                                        addName.setText(data.name)
                                        addDate.setText(data.yearOfBirth)
                                        addDegree.setText(data.degree)
                                        addDesc.setText(data.summary)
                                        addEmail.setText(data.email)
                                        addEducationInstitution.setText(data.educationInstitution)
                                        addPhone.setText(data.mobilePhone)
                                        addLanguage.setText(data.language)
                                        addSalaryMinimum.setText(data.salaryMinimum.toString())
                                        addSkills.setText(data.skills)
                                        addLocation.setText(data.location)
                                    }
                                }

                            }
                        is Resource.Error -> {
                            Toast.makeText(
                                this, resource.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {

                        }

                        else -> {}
                    }
                }

            }
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.addName.text.toString()
            val date = binding.addDate.text.toString()
            val degree = binding.addDegree.text.toString()
            val desc = binding.addDesc.text.toString()
            val email = binding.addEmail.text.toString()
            val educationInstitution = binding.addEducationInstitution.text.toString()
            val phone = binding.addPhone.text.toString()
            val language = binding.addLanguage.text.toString()
            val salaryMinimum = binding.addSalaryMinimum.text.toString().toInt()
            val skills = binding.addSkills.text.toString()
            val location = binding.addLocation.text.toString()

            viewModel.updateProfile(token, name, date, degree, desc, email, educationInstitution, phone, language, salaryMinimum, skills, location)

            Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
        }
    }
}