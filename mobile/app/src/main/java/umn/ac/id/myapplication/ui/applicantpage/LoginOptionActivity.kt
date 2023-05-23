package umn.ac.id.myapplication.ui.applicantpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityLoginOptionBinding
import umn.ac.id.myapplication.ui.companypage.LoginCompanyActivity

class LoginOptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonEmployeeSignin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.buttonCompanySignin.setOnClickListener {
            Intent(this, LoginCompanyActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}