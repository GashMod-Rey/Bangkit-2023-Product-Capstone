package umn.ac.id.myapplication.ui.applicantpage

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
import umn.ac.id.myapplication.databinding.ActivityLoginBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.data.UserSession
import umn.ac.id.myapplication.ui.model.User
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodel.LoginViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.LoginViewModelFactory


class LoginActivity : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="user")
    private lateinit var binding: ActivityLoginBinding
    private var socket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
            val username = binding.adUsernameLogin.text.toString().trim()
            val password = binding.adPasswordLogin.text.toString().trim()

            if(username.isEmpty() && password.isEmpty()){
                binding.adPasswordLogin.error = "Password is empty"
                binding.adUsernameLogin.error = "Username is empty"
            }
            else {
                loginViewModel.postLogin(username, password)
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

                            socket!!.on("SingIn") { ars ->
                                Log.e("ttttttttt", "${ars[0]}")
                                Log.e("sssssssss", "${ars[1]}")
                                runOnUiThread {
                                    applicant = Gson().fromJson(ars[1].toString(), umn.ac.id.myapplication.ui.model.User::class.java)
                                    startActivity(Intent(applicationContext, MainActivity::class.java))
                                }
                            }

                            val jsonObject = JSONObject()
                            jsonObject.put("user", username)
                            jsonObject.put("token", it.data?.token)
                            jsonObject.put("isOnline", true)
                            socket!!.emit("SingIn", username, jsonObject)

                            Intent(this@LoginActivity, MainActivity::class.java).also {
                                startActivity(it)
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
            Intent(this@LoginActivity, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    companion object {
        var applicant: User = User()
    }
}