package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.Socket
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umn.ac.id.myapplication.databinding.FragmentChatCompanyBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.companypage.LoginCompanyActivity
import umn.ac.id.myapplication.ui.companypage.MessageCompanyActivity
import umn.ac.id.myapplication.ui.companypage.adapter.UserAdapter
import umn.ac.id.myapplication.ui.data.GetChatResponse
import umn.ac.id.myapplication.ui.model.ChatData
import umn.ac.id.myapplication.ui.model.User
import java.lang.reflect.Type

class ChatCompanyFragment : Fragment() {

    private var mSocket: Socket? = null
    private lateinit var binding: FragmentChatCompanyBinding
    private val adapterUser = UserAdapter(arrayListOf(), object : UserAdapter.OnClickItem {
        override fun onClick(user: User) {
            val intent = Intent(requireContext(), MessageCompanyActivity::class.java)
            intent.putExtra("username", user.user)
            intent.putExtra("token", user.token)
//            createChat(user.token, LoginCompanyActivity.users.token)
            startActivity(intent)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatCompanyBinding.inflate(inflater, container, false)
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

        mSocket!!.emit("allUser", true)
        mSocket!!.on("allUser") { ars ->
            activity?.runOnUiThread {
                val usersList: Type = object : TypeToken<List<User>>() {}.type
                val userList = Gson().fromJson<List<User>>(ars[0].toString(), usersList)
                val filteredUserList = userList.filter { user ->
                    user.token != LoginCompanyActivity.company.token
                }

                adapterUser.apply {
                    data.clear()
                    data.addAll(filteredUserList)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun createChat(tokenApplicant: String, tokenCompany: String) {
        val chatData = ChatData(
            tokenA = tokenApplicant,
            tokenC = tokenCompany
        )

        val client = ApiClient.apiInstance.createNewChat(LoginCompanyActivity.company.token, chatData)
        client.enqueue(object : Callback<GetChatResponse> {
            override fun onResponse(call: Call<GetChatResponse>, response: Response<GetChatResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val message = apiResponse?.message ?: "No message provided"
                    Log.d("", "onResponse: $message")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        response.errorBody()?.toString()
                    } catch (e: JSONException) {
                        "Error retrieving error message"
                    }
                    Log.e("", "onResponse: $errorMessage")
                }
            }

            override fun onFailure(call: Call<GetChatResponse>, t: Throwable) {
                Log.e("", "onFailure: ${t.message}")
            }
        })
    }
}
