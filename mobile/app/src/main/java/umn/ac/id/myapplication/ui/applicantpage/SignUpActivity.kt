package umn.ac.id.myapplication.ui.applicantpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import io.socket.client.Socket
import umn.ac.id.myapplication.databinding.ActivitySignUpBinding
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var socket: Socket? = null
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.login.setOnClickListener{
            Intent(this@SignUpActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.buttonSignup.setOnClickListener {
            val username = binding.adUsernameSignup.text.toString().trim()
            val password = binding.adPasswordSignup.text.toString().trim()
            if(username.isEmpty() && password.isEmpty()){
                binding.adUsernameSignup.error = "Username is empty"
                binding.adPasswordSignup.error = "Password is empty"
            }
            else {
                signUpViewModel.postRegister(
                    username, password, socket!!
                )
                signUpViewModel.signup.observe(this){
                    when (it){
                        is Resource.Success -> {
                            val intent = Intent(this, LoginActivity::class.java)
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