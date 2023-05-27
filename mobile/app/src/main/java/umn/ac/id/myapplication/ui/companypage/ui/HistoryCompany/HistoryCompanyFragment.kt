package umn.ac.id.myapplication.ui.companypage.ui.HistoryCompany

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.databinding.FragmentHistoryBinding
import umn.ac.id.myapplication.databinding.FragmentHistoryCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.HistoryRejectActivity
import umn.ac.id.myapplication.ui.applicantpage.HistorySuccessActivity
import umn.ac.id.myapplication.ui.applicantpage.ui.history.HistoryFragment
import umn.ac.id.myapplication.ui.applicantpage.ui.history.HistoryViewModel
import umn.ac.id.myapplication.ui.companypage.HistoryDeclineCompanyActivity
import umn.ac.id.myapplication.ui.companypage.HistorySuccessCompanyActivity
import umn.ac.id.myapplication.ui.companypage.HistoryWaitingCompanyActivity

class HistoryCompanyFragment : Fragment() {

    private var _binding: FragmentHistoryCompanyBinding? = null

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
        _binding = FragmentHistoryCompanyBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.buttonSuccess.setOnClickListener {
            Intent(requireContext(), HistorySuccessCompanyActivity::class.java).also{
                startActivity(it)
            }
        }
        binding.buttonReject.setOnClickListener {
            Intent(requireContext(), HistoryDeclineCompanyActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.buttonWaiting.setOnClickListener {
            Intent(requireContext(), HistoryWaitingCompanyActivity::class.java).also {
                startActivity(it)
            }
        }
        return root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}