package umn.ac.id.myapplication.ui.chat.demo.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import io.socket.client.Socket
import org.json.JSONObject
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityChatBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.chat.model.Message
import umn.ac.id.myapplication.ui.chat.adapter.MessageAdapter
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory

class MessageActivity : AppCompatActivity() {

    private var mSocket: Socket? = null
    private lateinit var binding: ActivityChatBinding
    private val messageAdapter   = MessageAdapter(arrayListOf())

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val pref = UserPreferences.getInstance(dataStore)
    val mainViewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        binding = ActivityChatBinding.inflate(layoutInflater)
        mSocket = SocketManager.getInstance(this)!!.getSocket()

        binding.rcDataMes.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        mSocket!!.on("message") { arg ->
            runOnUiThread {
                val message = Gson().fromJson<Message>(arg[0].toString(), Message::class.java)
                messageAdapter.apply {
                    dataMessages.add(message)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun sendMessage() {
        val message = JSONObject()
        mainViewModel.getSession().observe(this){
            message.put("username", it.username)
            message.put("token", it.token)
            message.put("message", binding.inputMessage.text.toString())
        }

        mSocket!!.emit("message", message)
    }

}