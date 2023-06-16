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
import com.google.gson.Gson
import io.socket.client.Socket
import org.json.JSONObject
import umn.ac.id.myapplication.databinding.ActivityLoginCompanyBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.applicantpage.SignUpActivity
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession
import umn.ac.id.myapplication.ui.model.User
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodel.LoginViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.LoginViewModelFactory

class LoginCompanyActivity : AppCompatActivity() {



    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="user")
    private lateinit var binding: ActivityLoginCompanyBinding
    private var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, LoginViewModelFactory(pref))[LoginViewModel::class.java]

        socket = SocketManager.getInstance(this)!!.getSocket()

        socket!!.on(Socket.EVENT_CONNECT_ERROR) {
            runOnUiThread {
                Log.e("EVENT_CONNECT_ERROR", "EVENT_CONNECT_ERROR: ")
            }
        }
        socket!!.on(
            Socket.EVENT_CONNECT
        ) { Log.e("onConnect", "Socket Connected!") };
        socket!!.on(Socket.EVENT_DISCONNECT) {
            runOnUiThread {
                Log.e("onDisconnect", "Socket onDisconnect!")

            }
        }
        socket!!.connect()

        binding.buttonLogin.setOnClickListener {
            val username = binding.adEmailLogin.text.toString().trim()
            val password = binding.adPasswordLogin.text.toString().trim()

            if(username.isEmpty() && password.isEmpty()){
                binding.adPasswordLogin.error = "Password is empty"
                binding.adEmailLogin.error = "Username is empty"
            }
            else {
                loginViewModel.postLoginCompany(username, password)
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
//                            val intent = Intent(this, MainCompanyActivity::class.java)
                            socket!!.on("SingIn") { ars ->
                                runOnUiThread {
                                    company = Gson().fromJson(ars[1].toString(), User::class.java)
                                }
                            }

                            val jsonObject = JSONObject()
                            jsonObject.put("user", username)
                            jsonObject.put("token", it.data?.token)
                            jsonObject.put("isOnline", true)
                            socket!!.emit("SingIn", username, jsonObject)

                            Intent(this@LoginCompanyActivity, MainCompanyActivity::class.java).also { intent ->
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            startActivity(intent)

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

    companion object {
        var company : User = User()
    }
}