package umn.ac.id.myapplication.ui.companypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivitySettingsBinding

class SettingsCompanyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}