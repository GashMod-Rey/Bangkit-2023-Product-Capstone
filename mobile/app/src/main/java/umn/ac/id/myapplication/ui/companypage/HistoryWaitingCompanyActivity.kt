package umn.ac.id.myapplication.ui.companypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityHistoryRejectBinding
import umn.ac.id.myapplication.databinding.ActivityHistoryWaitingCompanyBinding
import umn.ac.id.myapplication.databinding.ActivityWaitingHiredCompanyBinding

class HistoryWaitingCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaitingHiredCompanyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitingHiredCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}