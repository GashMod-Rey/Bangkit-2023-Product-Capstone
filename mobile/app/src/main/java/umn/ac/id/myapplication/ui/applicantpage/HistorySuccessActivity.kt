package umn.ac.id.myapplication.ui.applicantpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityHistorySuccessBinding

class HistorySuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistorySuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}