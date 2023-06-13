package umn.ac.id.myapplication.ui.chat.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import umn.ac.id.myapplication.databinding.FragmentListBinding
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.chat.model.User
import umn.ac.id.myapplication.ui.chat.ui.viewmodel.UsersViewModel
import umn.ac.id.myapplication.ui.chat.other.ConfigUser
import umn.ac.id.myapplication.ui.chat.other.DATA_USER_NAME
import umn.ac.id.myapplication.ui.chat.other.GET_ALL_USER
import java.lang.reflect.Type

class ListUserFragment : Fragment() {

    private lateinit var mBinding: FragmentListBinding
    private lateinit var user: User
    private var mSocket: Socket? = null
    private var userString: String = ""
    private val viewMode by lazy { ViewModelProvider(requireActivity())[UsersViewModel::class.java] }

//    private val userAdapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentListBinding.inflate(layoutInflater, container, false)
            .apply { executePendingBindings() }
        mSocket!!.on(GET_ALL_USER, onUserList)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSocket = SocketManager.getInstance(requireContext())!!.getSocket()
        userString = ConfigUser.getInstance(requireContext())!!.getPreferences()!!
            .getString(DATA_USER_NAME, "")!!
        Log.d("", "onUserString: $userString")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mBinding.rcDataUser.apply {
//            adapter = userAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }

//        viewMode.getAllUser().observe(viewLifecycleOwner) {
//            userAdapter.apply {
//                dataList = it
//            }
//        }

//        userAdapter.setItemClickListener { user, _ ->
//            findNavController()
//                .navigate(
//                    R.id.globalActionToChatFragment,
//                    Bundle().apply {
//                        this.putString(USER_ID, (user as User).id)
//                        this.putString(TYPE_CHAT, USER_FRAGMENT)
//                        this.putString(USER_NAME, user.name)
//                    }
//                )
//        }
    }

    private var onUserList = Emitter.Listener { args ->
        GlobalScope.launch(Dispatchers.Main) {
            val length = args.size
            if (length == 0) {
                return@launch
            }
            val userListToken: Type = object : TypeToken<ArrayList<User>>() {}.type
            val userList =
                Gson().fromJson<ArrayList<User>>(
                    args[0].toString(),
                    userListToken
                )
            user.isOnline = true
            userList.remove(user)
            viewMode.addListUsers(userList)
        }
    }

}