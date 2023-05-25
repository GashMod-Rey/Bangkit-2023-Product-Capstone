package umn.ac.id.myapplication.ui.applicantpage.ui.history

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.FragmentHistoryBinding
import umn.ac.id.myapplication.databinding.FragmentProfileBinding
import umn.ac.id.myapplication.ui.applicantpage.HistoryRejectActivity
import umn.ac.id.myapplication.ui.applicantpage.HistorySuccessActivity

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.buttonSuccess.setOnClickListener {
            Intent(requireContext(), HistorySuccessActivity::class.java).also{
                startActivity(it)
            }
        }
        binding.buttonReject.setOnClickListener {
            Intent(requireContext(), HistoryRejectActivity::class.java).also {
                startActivity(it)
            }
        }
        return root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}