package umn.ac.id.myapplication.ui.companypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivitySignUpBinding
import umn.ac.id.myapplication.ui.applicantpage.LoginActivity

class RegisterCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.login.setOnClickListener{
            Intent(this@RegisterCompanyActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.buttonSignup.setOnClickListener {
            Intent(this@RegisterCompanyActivity, LoginActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}