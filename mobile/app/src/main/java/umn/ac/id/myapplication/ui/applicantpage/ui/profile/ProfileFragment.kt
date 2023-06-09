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
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val userPreferences = UserPreferences.getInstance(requireContext().dataStore)
        val profileViewModel =
            ViewModelProvider(this, ProfileViewModelFactory(userPreferences)).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel.getSession().observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
                Log.d("TAG", "onCreate: $token")
                profileViewModel.cvData.observe(viewLifecycleOwner){
                    when(it){
                        is Resource.Success -> {
                            it.data?.let {
                                data ->
                                binding.tvName.text = data.Name
                                binding.tvRole.text = data.Skills
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireContext(), it.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading ->{

                        }
                    }
                }
            }
        }



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
}