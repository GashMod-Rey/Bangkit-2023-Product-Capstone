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

//        viewModel.cvData.observe(this){
//            cvData ->
//            cvData?.let {
//                binding.addName.setText(cvData.data?.Name)
//                binding.addDate.setText(cvData.data?.YearOfBirth)
//                binding.addDegree.setText(cvData.data?.Degree)
//                binding.addDesc.setText(cvData.data?.Summary)
//                binding.addEmail.setText(cvData.data?.Email)
//                binding.addEducationInstitution.setText(cvData.data?.EducationInstitution)
//                binding.addPhone.setText(cvData.data?.MobilePhone)
//                binding.addLanguage.setText(cvData.data?.Language)
//                binding.addSalaryMinimum.setText(cvData.data?.SalaryMinimum.toString())
//                binding.addSkills.setText(cvData.data?.Skills)
//                binding.addLocation.setText(cvData.data?.Location)
//            }
//        }
        viewModel.getSession().observe(this){
            if(it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")
                viewModel.getCvData(token)
                viewModel.cvData.observe(this) {
                    when(it){
                        is Resource.Success -> {
                            it.data?.let { data ->
                                binding.addName.setText(data.name)
                                binding.addDate.setText(data.yearOfBirth)
                                binding.addDegree.setText(data.degree)
                                binding.addDesc.setText(data.summary)
                                binding.addEmail.setText(data.email)
                                binding.addEducationInstitution.setText(data.educationInstitution)
                                binding.addPhone.setText(data.mobilePhone)
                                binding.addLanguage.setText(data.language)
                                binding.addSalaryMinimum.setText(data.salaryMinimum.toString())
                                binding.addSkills.setText(data.skills)
                                binding.addLocation.setText(data.location)
                            }

                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this, it.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {

                        }

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