package umn.ac.id.myapplication.ui.applicantpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityUploadCvBinding

class UploadCvActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadCvBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadCvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCv.setOnClickListener {

        }

        binding.buttonUpload.setOnClickListener {
            Intent(this, UploadCvSuccessActivity::class.java ).also {
                startActivity(it)
            }
        }
    }
}