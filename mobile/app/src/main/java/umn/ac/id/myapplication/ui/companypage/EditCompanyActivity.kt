package umn.ac.id.myapplication.ui.companypage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityAboutMeChangeBinding
import umn.ac.id.myapplication.databinding.ActivityEditCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.data.UserPreferences
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
        val viewModel = ViewModelProvider(this, ProfileViewModelFactory(pref))[ProfileViewModel::class.java]

    }
}