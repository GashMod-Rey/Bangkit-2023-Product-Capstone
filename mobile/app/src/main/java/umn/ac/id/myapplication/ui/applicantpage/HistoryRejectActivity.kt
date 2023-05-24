package umn.ac.id.myapplication.ui.applicantpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.databinding.ActivityHistoryRejectBinding

class HistoryRejectActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHistoryRejectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryRejectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}