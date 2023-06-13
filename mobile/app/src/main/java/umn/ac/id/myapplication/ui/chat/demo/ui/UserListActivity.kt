package umn.ac.id.myapplication.ui.chat.demo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.Socket
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityListUserBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.chat.model.User
import umn.ac.id.myapplication.ui.chat.adapter.UserAdapter
import java.lang.reflect.Type

class UserListActivity : AppCompatActivity() {

    private var mSocket: Socket? = null
    private lateinit var binding: ActivityListUserBinding

    private val adapterUser = UserAdapter(arrayListOf(), object: UserAdapter.OnClickItem {
        override fun onClick(user: User) {
            startActivity(Intent(applicationContext, MessageActivity::class.java))
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)
        mSocket = SocketManager.getInstance(this)!!.getSocket()

        binding = ActivityListUserBinding.inflate(layoutInflater)

        binding.rcDataUser.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterUser
        }

        mSocket!!.emit("allUser", true)
        mSocket!!.on("allUser") { ars ->
            val usersList: Type = object : TypeToken<List<User>>() {}.type
            val userList = Gson().fromJson<List<User>>(ars[0].toString(), usersList)

            adapterUser.apply {
                data.addAll(userList)
                notifyDataSetChanged()
            }
        }
    }

}