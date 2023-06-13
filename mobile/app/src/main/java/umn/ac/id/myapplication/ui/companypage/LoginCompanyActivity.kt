package umn.ac.id.myapplication.ui.companypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import io.socket.client.Socket
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityLoginBinding
import umn.ac.id.myapplication.databinding.ActivityLoginCompanyBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.applicantpage.MainActivity
import umn.ac.id.myapplication.ui.applicantpage.SignUpActivity
import umn.ac.id.myapplication.ui.chat.other.ConfigUser
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodel.LoginViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.LoginViewModelFactory

class LoginCompanyActivity : AppCompatActivity() {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="user")
    private lateinit var binding: ActivityLoginCompanyBinding
    private var socket: Socket? = null
    private val configUser by lazy {
        ConfigUser.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, LoginViewModelFactory(pref))[LoginViewModel::class.java]

        socket = SocketManager.getInstance(this)!!.getSocket()

        binding.buttonLogin.setOnClickListener {
            val username = binding.adEmailLogin.text.toString().trim()
            val password = binding.adPasswordLogin.text.toString().trim()

            if(username.isEmpty() && password.isEmpty()){
                binding.adPasswordLogin.error = "Password is empty"
                binding.adEmailLogin.error = "Username is empty"
            }
            else {
                loginViewModel.postLoginCompany(username, password, socket!!, configUser!!)
                loginViewModel.login.observe(this){
                    when(it){
                        is Resource.Success -> {
                            loginViewModel.saveSession(
                                UserSession(
                                    true,
                                    "Bearer " + it.data?.token,
                                    username
                                )
                            )
                            val intent = Intent(this, MainCompanyActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                            finish()
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this, it.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resource.Loading -> {

                        }
                    }
                }
            }

        }

        binding.Signup.setOnClickListener {
            Intent(this@LoginCompanyActivity, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}