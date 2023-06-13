package umn.ac.id.myapplication.ui.companypage

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nurbk.ps.demochat.ui.fragment.MainFragment
import io.socket.client.Socket
import org.json.JSONObject
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.chat.model.User
import umn.ac.id.myapplication.ui.chat.other.*

import umn.ac.id.myapplication.ui.companypage.ui.HistoryCompany.HistoryCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany.ProfileCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.home.HomeCompanyFragment
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory

class MainCompanyActivity : AppCompatActivity() {

    private lateinit var bottomNavigationview: BottomNavigationView;
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val userString by lazy {
        ConfigUser.getInstance(this)!!.getPreferences()!!.getString(DATA_USER_NAME, "")
    }
    private lateinit var user: User
    private var mSocket: Socket? = null
    private var isDataShow = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_company)

        val pref = UserPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]

        mSocket = SocketManager.getInstance(this)!!.getSocket()

        mSocket!!.on(Socket.EVENT_CONNECT_ERROR) {}
        mSocket!!.on(Socket.EVENT_CONNECT) {}
        mSocket!!.on(Socket.EVENT_DISCONNECT) {}

        mainViewModel.getSession().observe(this){
            if (it.isLogin){
                isDataShow = savedInstanceState?.getBoolean(IS_CONNECTING) ?: true
                if (isDataShow) {
                    mSocket!!.emit(GET_ALL_USER, true)
                    mSocket!!.emit(UPDATE_DATA, JSONObject().apply {
                        put(User.USERNAME, it.username)
                        put(User.IS_ONLINE, true)
                    })
                }
            }
        }

        bottomNavigationview = findViewById(R.id.bottom_nav)

        bottomNavigationview.setOnNavigationItemSelectedListener{
                item ->
            var selectedFragment: Fragment? = null

            when (item.itemId){
                R.id.navigation_home_company -> selectedFragment = HomeCompanyFragment()
                R.id.navigation_chat_company -> selectedFragment = MainFragment()
                R.id.navigation_history_company -> selectedFragment = HistoryCompanyFragment()
                R.id.navigation_profile_company -> selectedFragment = ProfileCompanyFragment()
            }

            if(selectedFragment != null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, selectedFragment)
                    .commit()
            }

            true

        }

        bottomNavigationview.selectedItemId = R.id.navigation_home_company

    }

    override fun onDestroy() {
        if (mSocket != null)
            mSocket!!.emit(UPDATE_DATA, JSONObject().apply {
                put(umn.ac.id.myapplication.ui.applicantpage.ui.model.User.ID, user.id)
                put(umn.ac.id.myapplication.ui.applicantpage.ui.model.User.IS_ONLINE, false)
            })
        super.onDestroy()
    }
}