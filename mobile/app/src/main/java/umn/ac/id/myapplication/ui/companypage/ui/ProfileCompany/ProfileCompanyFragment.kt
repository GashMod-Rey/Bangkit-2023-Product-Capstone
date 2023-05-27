package umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.databinding.FragmentHomeCompanyBinding
import umn.ac.id.myapplication.databinding.FragmentProfileBinding
import umn.ac.id.myapplication.databinding.FragmentProfileCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.AboutMeActivity
import umn.ac.id.myapplication.ui.applicantpage.AboutMeChangeActivity
import umn.ac.id.myapplication.ui.applicantpage.SettingsActivity
import umn.ac.id.myapplication.ui.applicantpage.UploadCvActivity
import umn.ac.id.myapplication.ui.applicantpage.ui.profile.ProfileViewModel
import umn.ac.id.myapplication.ui.companypage.SettingsCompanyActivity


class ProfileCompanyFragment : Fragment() {

    private var _binding: FragmentProfileCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val profileViewModel =
            ViewModelProvider(this).get(ProfileCompanyViewModel::class.java)

        _binding = FragmentProfileCompanyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonedit = binding.buttonEdit
        val buttonsetting = binding.buttonSettings


        buttonedit.setOnClickListener{
            val intent = Intent(requireContext(), AboutMeChangeActivity::class.java)
            startActivity(intent)
        }

        buttonsetting.setOnClickListener{
            val intent = Intent(requireContext(), SettingsCompanyActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}