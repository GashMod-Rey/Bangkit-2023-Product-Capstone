package umn.ac.id.myapplication.ui.applicantpage.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.databinding.FragmentChatBinding
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.Socket
import umn.ac.id.myapplication.databinding.FragmentProfileBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.applicantpage.LoginActivity
import umn.ac.id.myapplication.ui.applicantpage.MessageActivity
import umn.ac.id.myapplication.ui.applicantpage.adapter.UserAdapter
import umn.ac.id.myapplication.ui.model.User
import java.lang.reflect.Type


class ChatFragment : Fragment() {

    private var mSocket: Socket? = null
    private lateinit var binding: FragmentChatBinding
    private val adapterUser = UserAdapter(arrayListOf(), object : UserAdapter.OnClickItem {
        override fun onClick(user: User) {
            val intent = Intent(requireContext(), MessageActivity::class.java)
            intent.putExtra("username", user.user)
            intent.putExtra("token", user.token)
            startActivity(intent)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSocket = SocketManager.getInstance(requireContext())!!.getSocket()

        binding.rvChatList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterUser
        }

        Log.d("", "USER: ${LoginActivity.applicant.user} | ${LoginActivity.applicant.token}")

        mSocket!!.emit("allUser", true)
        mSocket!!.on("allUser") { ars ->
            activity?.runOnUiThread {
                val usersList: Type = object : TypeToken<List<User>>() {}.type
                val userList = Gson().fromJson<List<User>>(ars[0].toString(), usersList)
                val filteredUserList = userList.filter { user ->
                    user.token != LoginActivity.applicant.token
                }

                adapterUser.apply {
                    data.clear()
                    data.addAll(filteredUserList)
                    notifyDataSetChanged()
                }
            }
        }
    }

}