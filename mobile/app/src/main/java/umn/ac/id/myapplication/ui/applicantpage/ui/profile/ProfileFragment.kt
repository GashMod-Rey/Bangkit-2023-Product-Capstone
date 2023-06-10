package umn.ac.id.myapplication.ui.applicantpage.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.databinding.FragmentProfileBinding
import umn.ac.id.myapplication.ui.applicantpage.AboutMeActivity
import umn.ac.id.myapplication.ui.applicantpage.AboutMeChangeActivity
import umn.ac.id.myapplication.ui.applicantpage.SettingsActivity
import umn.ac.id.myapplication.ui.applicantpage.UploadCvActivity
import umn.ac.id.myapplication.ui.data.UserPreferences
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodelfactory.ProfileViewModelFactory

class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val Context.dataStore by preferencesDataStore(name = "user")
    private lateinit var token: String
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
        profileViewModel =
            ViewModelProvider(this, ProfileViewModelFactory(userPreferences)).get(ProfileViewModel::class.java)

        val buttonAboutMe = binding.buttonAboutme
        val buttonCV = binding.buttonCv
        val buttonEdit = binding.buttonEdit
        val buttonSetting = binding.buttonSettings

        buttonAboutMe.setOnClickListener {
            val intent = Intent(requireContext(), AboutMeActivity::class.java)
            startActivity(intent)
        }

        buttonCV.setOnClickListener {
            val intent = Intent(requireContext(), UploadCvActivity::class.java)
            startActivity(intent)
        }

        buttonEdit.setOnClickListener{
            val intent = Intent(requireContext(), AboutMeChangeActivity::class.java)
            startActivity(intent)
        }

        buttonSetting.setOnClickListener{
            val intent = Intent(requireContext(), SettingsActivity::class.java)
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
                    profileViewModel.getCvData(token)
                }
                else {
                    Toast.makeText(requireContext(), "Token is null", Toast.LENGTH_SHORT).show()
                }

            }
        }

                profileViewModel.cvData.observe(viewLifecycleOwner){ resource ->
                    when(resource){
                        is Resource.Success -> {
                            val data = resource.data
                            binding.tvName.text = data?.name
                            binding.tvRole.text = data?.skills
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireContext(), resource.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading ->{

                        }
                    }
                }
            }
        }



