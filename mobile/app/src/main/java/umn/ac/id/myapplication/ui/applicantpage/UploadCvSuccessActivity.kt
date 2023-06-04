package umn.ac.id.myapplication.ui.applicantpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityUploadCvSuccessBinding
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileFragment

class UploadCvSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadCvSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadCvSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDone.setOnClickListener {
            Intent(this, AboutMeActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}