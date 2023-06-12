package umn.ac.id.myapplication.ui.applicantpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityAboutMeBinding
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.data.ProfileApplicantResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodelfactory.ProfileViewModelFactory

class AboutMeActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

    private var token = ""
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityAboutMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = UserPreferences.getInstance(dataStore)

        profileViewModel = ViewModelProvider(this, ProfileViewModelFactory(pref))[ProfileViewModel::class.java]

        profileViewModel.getSession().observe(this){
            if (it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")
                profileViewModel.getCvData(token)
                profileViewModel.cvData.observe(this) { resource ->
                    when(resource){
                        is Resource.Success -> {
                            val profileResponse = resource.data
                            profileResponse?.let { data ->
                                    binding.tvName.text = "Name: ${data.name}"
                                    binding.tvAge.text = "Year Of Birth: ${data.yearOfBirth}"
                                    binding.tvDesc.text = "Summary: ${data.summary}"
                                    binding.tvEducationInstitution.text = "Education Institution: ${data.educationInstitution}"
                                    binding.tvLanguage.text = "Language: ${data.language}"
                                    binding.tvEmail.text = "Email : ${data.email}"
                                    binding.tvMobilePhone.text = "Phone : ${data.mobilePhone}"
                                    binding.tvSkills.text = "Skills: ${data.skills}"
                                    binding.tvDegree.text = "Degree: ${data.degree}"
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
//        val token = "ini diisi token kita nay"

//        profileViewModel.cvData.observe(this) { resource ->
//            when (resource) {
//                is Resource.Loading -> {
//                    // Tampilkan indikator loading atau lakukan tindakan yang sesuai saat data sedang dimuat
//                    showLoadingIndicator()
//                }
//                is Resource.Success -> {
//                    val profileApplicantResponse = resource.data
//                    // Lakukan tindakan yang diperlukan dengan data profileApplicantResponse
//                    hideLoadingIndicator()
//                    if (profileApplicantResponse != null) {
//                        displayProfileData(profileApplicantResponse)
//                    }
//                }
//                is Resource.Error -> {
//                    val errorMessage = resource.message
//                    // Lakukan tindakan yang diperlukan ketika terjadi kesalahan
//                    hideLoadingIndicator()
//                    if (errorMessage != null) {
//                        showError(errorMessage)
//                    }
//                }
//            }
//        }

//        profileViewModel.getCvData(token)
//    }
//
//    private fun showLoadingIndicator() {
//        // Tampilkan indikator loading seperti ProgressBar
//        progressBar.visibility = View.VISIBLE
//    }
//
//    private fun hideLoadingIndicator() {
//        // Sembunyikan indikator loading
//        progressBar.visibility = View.GONE
//    }
//
//    private fun displayProfileData(profileApplicantResponse: ProfileApplicantResponse) {
//        // Tampilkan data profil di antarmuka pengguna
//        findViewById<TextView>(R.id.tvDesc).text = profileApplicantResponse.Summary
////        findViewById<TextView>(R.id.tvAge).text = "Age: ${profileApplicantResponse.Age} years of age"
//        findViewById<TextView>(R.id.tvSkills).text = "Skills: ${profileApplicantResponse.Skills}"
//        findViewById<TextView>(R.id.tvEmail).text = "Email: ${profileApplicantResponse.Email}"
//        findViewById<TextView>(R.id.tvYearOfBirth).text = "Date of Birth: ${profileApplicantResponse.YearOfBirth}"
//        findViewById<TextView>(R.id.tvLanguage).text = "Language: ${profileApplicantResponse.Language}"
//        findViewById<TextView>(R.id.tvEducationInstitution).text = "Education Institution: ${profileApplicantResponse.EducationInstitution}"
//        findViewById<TextView>(R.id.tvDegree).text = "Degree: ${profileApplicantResponse.Degree}"
//        findViewById<TextView>(R.id.tvMobilePhone).text = "Mobile Phone: ${profileApplicantResponse.MobilePhone}"
//    }
//
//
//    private fun showError(errorMessage: String) {
//        // Tampilkan pesan kesalahan ke pengguna
//        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
