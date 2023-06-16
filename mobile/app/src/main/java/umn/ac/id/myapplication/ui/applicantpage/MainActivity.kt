package umn.ac.id.myapplication.ui.applicantpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.ui.applicantpage.chat.ChatFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.history.HistoryFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.home.HomeFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationview: BottomNavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationview = findViewById(R.id.bottom_nav)

        bottomNavigationview.setOnNavigationItemSelectedListener{
            item ->
            var selectedFragment: Fragment? = null

            when (item.itemId){
                R.id.navigation_home -> selectedFragment = HomeFragment()
                R.id.navigation_chat -> selectedFragment = ChatFragment()
                R.id.navigation_history -> selectedFragment = HistoryFragment()
                R.id.navigation_profile -> selectedFragment = ProfileFragment()
            }

            if(selectedFragment != null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, selectedFragment)
                    .commit()
            }

            true

        }

        bottomNavigationview.selectedItemId = R.id.navigation_home

    }
}