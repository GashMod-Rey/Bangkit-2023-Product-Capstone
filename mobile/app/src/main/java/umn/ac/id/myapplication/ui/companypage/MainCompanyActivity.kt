package umn.ac.id.myapplication.ui.companypage

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityMainCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.ui.chat.ChatFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.history.HistoryFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.home.HomeFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileFragment
import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.ChatCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.HistoryCompany.HistoryCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany.ProfileCompanyFragment
import umn.ac.id.myapplication.ui.companypage.ui.home.HomeCompanyFragment

class MainCompanyActivity : AppCompatActivity() {

    private lateinit var bottomNavigationview: BottomNavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_company)

        bottomNavigationview = findViewById(R.id.bottom_nav)

        bottomNavigationview.setOnNavigationItemSelectedListener{
                item ->
            var selectedFragment: Fragment? = null

            when (item.itemId){
                R.id.navigation_home_company -> selectedFragment = HomeCompanyFragment()
                R.id.navigation_chat_company -> selectedFragment = ChatCompanyFragment()
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
}