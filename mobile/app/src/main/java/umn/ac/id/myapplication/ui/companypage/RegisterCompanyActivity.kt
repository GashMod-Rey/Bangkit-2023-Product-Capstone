package umn.ac.id.myapplication.ui.companypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import io.socket.client.Socket
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityRegisterCompanyBinding
import umn.ac.id.myapplication.databinding.ActivitySignUpBinding
import umn.ac.id.myapplication.ui.applicantpage.LoginActivity
import umn.ac.id.myapplication.ui.chat.other.ConfigUser
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodel.SignUpViewModel

class RegisterCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterCompanyBinding
    private var socket: Socket? = null
    private val configUser by lazy {
        ConfigUser.getInstance(applicationContext)
    }
    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterCompanyBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.login.setOnClickListener{
            Intent(this@RegisterCompanyActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.buttonSignup.setOnClickListener {
            val username = binding.adEmailSignup.text.toString().trim()
            val password = binding.adPasswordSignup.text.toString().trim()
            if(username.isEmpty() && password.isEmpty()){
                binding.adEmailSignup.error = "Username is empty"
                binding.adPasswordSignup.error = "Password is empty"
            }
            else {
                signUpViewModel.postRegister(
                    username, password, socket!!
                )
                signUpViewModel.signupcompany.observe(this){
                    when (it){
                        is Resource.Success -> {
                            val intent = Intent(this, LoginCompanyActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this, it.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resource.Loading-> {

                        }
                    }
                }
            }


        }
    }
}