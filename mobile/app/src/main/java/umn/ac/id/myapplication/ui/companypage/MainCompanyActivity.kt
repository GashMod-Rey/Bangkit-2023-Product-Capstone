package umn.ac.id.myapplication.ui.companypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.socket.client.Socket
import org.json.JSONObject
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.ui.api.SocketManager
import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.ChatCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.HistoryCompany.HistoryCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany.ProfileCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.home.HomeCompanyFragment
import umn.ac.id.myapplication.ui.data.UserDataResponse
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.viewmodel.MainViewModel
import umn.ac.id.myapplication.ui.viewmodelfactory.MainViewModelFactory
import com.google.gson.Gson

class MainCompanyActivity : AppCompatActivity() {

    private lateinit var bottomNavigationview: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_company)

        bottomNavigationview = findViewById(R.id.bottom_nav)

        bottomNavigationview.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.navigation_home_company -> selectedFragment = HomeCompanyFragment()
                R.id.navigation_chat_company -> selectedFragment = ChatCompanyFragment()
                R.id.navigation_history_company -> selectedFragment = HistoryCompanyFragment()
                R.id.navigation_profile_company -> selectedFragment = ProfileCompanyFragment()
            }

            val userDataJson = intent.getStringExtra("fil_user")
            if (userDataJson != null) {
                val bundle = Bundle()
                bundle.putString("fil_user", userDataJson)
                selectedFragment?.arguments = bundle
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, selectedFragment)
                    .commit()
            }

            true
        }

        bottomNavigationview.selectedItemId = R.id.navigation_home_company
    }
}
