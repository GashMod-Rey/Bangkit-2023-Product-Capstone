package umn.ac.id.myapplication.ui.companypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityJobPreferencesBinding

class JobPreferencesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobPreferencesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chipGroupSkills = binding.chipGroupSkills
        val chipGroupLanguage = binding.chipGroupLanguage
    }
}