package umn.ac.id.myapplication.ui.companypage

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivitySettingsBinding
import umn.ac.id.myapplication.databinding.ActivitySettingsCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.LoginActivity

class SettingsCompanyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsCompanyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogout.setOnClickListener {
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