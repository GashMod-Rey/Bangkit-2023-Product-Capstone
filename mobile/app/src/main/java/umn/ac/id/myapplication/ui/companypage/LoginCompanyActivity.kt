package umn.ac.id.myapplication.ui.companypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityLoginBinding
import umn.ac.id.myapplication.ui.applicantpage.MainActivity
import umn.ac.id.myapplication.ui.applicantpage.SignUpActivity

class LoginCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_company)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            Intent(this, MainCompanyActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.Signup.setOnClickListener {
            Intent(this@LoginCompanyActivity, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}