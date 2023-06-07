package umn.ac.id.myapplication.ui.applicantpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.data.ProfileApplicantResponse
import umn.ac.id.myapplication.ui.utils.Resource

class AboutMeActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val token = "ini diisi token kita nay"

        profileViewModel.cvData.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Tampilkan indikator loading atau lakukan tindakan yang sesuai saat data sedang dimuat
                    showLoadingIndicator()
                }
                is Resource.Success -> {
                    val profileApplicantResponse = resource.data
                    // Lakukan tindakan yang diperlukan dengan data profileApplicantResponse
                    hideLoadingIndicator()
                    if (profileApplicantResponse != null) {
                        displayProfileData(profileApplicantResponse)
                    }
                }
                is Resource.Error -> {
                    val errorMessage = resource.message
                    // Lakukan tindakan yang diperlukan ketika terjadi kesalahan
                    hideLoadingIndicator()
                    if (errorMessage != null) {
                        showError(errorMessage)
                    }
                }
            }
        }

        profileViewModel.getCvData(token)
    }

    private fun showLoadingIndicator() {
        // Tampilkan indikator loading seperti ProgressBar
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        // Sembunyikan indikator loading
        progressBar.visibility = View.GONE
    }

    private fun displayProfileData(profileApplicantResponse: ProfileApplicantResponse) {
        // Tampilkan data profil di antarmuka pengguna
        findViewById<TextView>(R.id.tvDesc).text = profileApplicantResponse.Summary
//        findViewById<TextView>(R.id.tvAge).text = "Age: ${profileApplicantResponse.Age} years of age"
        findViewById<TextView>(R.id.tvSkills).text = "Skills: ${profileApplicantResponse.Skills}"
        findViewById<TextView>(R.id.tvEmail).text = "Email: ${profileApplicantResponse.Email}"
        findViewById<TextView>(R.id.tvYearOfBirth).text = "Date of Birth: ${profileApplicantResponse.YearOfBirth}"
        findViewById<TextView>(R.id.tvLanguage).text = "Language: ${profileApplicantResponse.Language}"
        findViewById<TextView>(R.id.tvEducationInstitution).text = "Education Institution: ${profileApplicantResponse.EducationInstitution}"
        findViewById<TextView>(R.id.tvDegree).text = "Degree: ${profileApplicantResponse.Degree}"
        findViewById<TextView>(R.id.tvMobilePhone).text = "Mobile Phone: ${profileApplicantResponse.MobilePhone}"
    }


    private fun showError(errorMessage: String) {
        // Tampilkan pesan kesalahan ke pengguna
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
