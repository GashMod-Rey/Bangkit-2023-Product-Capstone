package umn.ac.id.myapplication.ui.applicantpage

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivitySettingsBinding
import umn.ac.id.myapplication.ui.applicantpage.ui.chat.ChatFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.history.HistoryFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.model.home.HomeFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileFragment

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAboutme.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Logout Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to logout?")
        alertDialogBuilder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            performLogout()
        }
        alertDialogBuilder.setNegativeButton("No", null)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun performLogout() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}