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
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityAboutMeChangeBinding
import umn.ac.id.myapplication.databinding.ActivityEditCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.AboutMeActivity
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany.ProfileCompanyViewModel
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodelfactory.ProfileCompanyViewModelFactory
import umn.ac.id.myapplication.ui.viewmodelfactory.ProfileViewModelFactory

class EditCompanyActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private lateinit var binding: ActivityEditCompanyBinding
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, ProfileCompanyViewModelFactory(pref))[ProfileCompanyViewModel::class.java]

        viewModel.getSession().observe(this){
            if(it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")
                viewModel.getCompanyData(token)
                viewModel.companyData.observe(this) { resource ->
                    when(resource){
                        is Resource.Success -> {
                            val companyApplicant = resource.data
                            companyApplicant?.let { data ->
                                binding.apply{
                                    addName.setText(data.Name)
                                    adddescription.setText(data.Summary)
                                    addemployeesize.setText(data.Employee.toString())
                                    addLocation.setText(data.Location)
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
                viewModel.updateCompanyData.observe(this){
                        resource ->
                    when(resource){
                        is Resource.Success -> {
                            Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(this, "Gagal memperbarui profil: ${resource.message}", Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
                binding.buttonAccept.setOnClickListener {
                    val name = binding.addName.text.toString()
                    val location = binding.addLocation.toString()
                    val employee = binding.addemployeesize.toString()
                    val summary = binding.adddescription.toString()

                    val previousData = viewModel.companyData.value?.data

                    val newName = if(name.isNotBlank()) name else previousData?.Name.orEmpty()
                    val newEmployeeSize = if(employee.isNotBlank()) employee.toIntOrNull()?:0 else previousData?.Employee?:0
                    val newSummary = if(summary.isNotBlank()) summary else previousData?.Summary.orEmpty()
                    val newLocation = if(location.isNotBlank()) location else previousData?.Location.orEmpty()


                    viewModel.updateProfileCompany(token, newName, newSummary, newLocation, newEmployeeSize)

                    }
                }
            }

        }
}