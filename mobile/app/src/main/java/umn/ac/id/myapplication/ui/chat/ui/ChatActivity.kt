//package umn.ac.id.myapplication.ui.chat.ui
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.google.gson.Gson
//import io.socket.client.Socket
//import org.json.JSONObject
//import umn.ac.id.myapplication.databinding.ActivityMainBinding
//import umn.ac.id.myapplication.ui.api.SocketManager
//import umn.ac.id.myapplication.ui.chat.model.User
//import umn.ac.id.myapplication.ui.chat.other.*
//import java.lang.Exception
//
//class ChatActivity : AppCompatActivity() {
//
////    private lateinit var mBinding: ActivityMainBinding
////    private val userString by lazy {
////        ConfigUser.getInstance(this)!!.getPreferences()!!.getString(DATA_USER_NAME, "")
////    }
//
////    private lateinit var user: User
////    private var mSocket: Socket? = null
////    private var isDataShow = true
//
//    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        mBinding = ActivityMainBinding.inflate(layoutInflater)
////        setContentView(mBinding.root)
//
////        mSocket = SocketManager.getInstance(this)!!.getSocket()
////
////        mSocket!!.on(Socket.EVENT_CONNECT_ERROR) {}
////        mSocket!!.on(Socket.EVENT_CONNECT) {}
////        mSocket!!.on(Socket.EVENT_DISCONNECT) {}
//
//        user = try {
//            Gson().fromJson(userString, User::class.java)
//        } catch (e: Exception) {
//            User()
//        }
//
////        isDataShow = savedInstanceState?.getBoolean(IS_CONNECTING) ?: true
////        if (isDataShow) {
////            mSocket!!.emit(GET_ALL_USER, true)
////            mSocket!!.emit(UPDATE_DATA, JSONObject().apply {
////                put(umn.ac.id.myapplication.ui.applicantpage.ui.model.User.ID, user.id)
////                put(umn.ac.id.myapplication.ui.applicantpage.ui.model.User.IS_ONLINE, true)
////            })
////        }
//
//        val dataShared = ConfigUser.getInstance(this)!!.getPreferences()
//
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putBoolean(IS_CONNECTING, false)
//    }
//
//
////    override fun onDestroy() {
////        if (mSocket != null)
////            mSocket!!.emit(UPDATE_DATA, JSONObject().apply {
////                put(umn.ac.id.myapplication.ui.applicantpage.ui.model.User.ID, user.id)
////                put(umn.ac.id.myapplication.ui.applicantpage.ui.model.User.IS_ONLINE, false)
////            })
////        super.onDestroy()
////    }
//
//}