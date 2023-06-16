package umn.ac.id.myapplication.ui.companypage.ui.ProfileCompany

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
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
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.utils.Resource


class ProfileCompanyFragment : Fragment() {

    private var _binding: FragmentProfileCompanyBinding? = null
    private val Context.dataStore by preferencesDataStore(name = "user")
    private lateinit var token: String
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileCompanyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSession()
    }
    private fun observeSession(){
        profileViewModel.getSession().observe(viewLifecycleOwner){
                session ->
            if (session.isLogin){
                token = session.token
                if(token != null){
                    profileViewModel.getCompanyData(token)
                }
                else {
                    Toast.makeText(requireContext(), "Token is null", Toast.LENGTH_SHORT).show()
                }

            }
        }

        profileViewModel.companyData.observe(viewLifecycleOwner){ resource ->
            when(resource){
                is Resource.Success -> {
                    val profileApplicant = resource.data
                    profileApplicant?.let{ data ->
                        binding.apply {
                            titleCompany.text = data.Name
                            descriptionAboutCompany.text = data.Summary
                            descriptionEmployeeSize.text = data.Employee.toString()
                            descriptionLocation.text = data.Location

                        }

                    }

                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(), resource.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading ->{

                }
                else -> {}
            }
        }
    }
}