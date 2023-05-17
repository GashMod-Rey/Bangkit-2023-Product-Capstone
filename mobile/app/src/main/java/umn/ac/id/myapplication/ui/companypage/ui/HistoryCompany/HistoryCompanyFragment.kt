package umn.ac.id.myapplication.ui.companypage.ui.HistoryCompany

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.databinding.FragmentHistoryCompanyBinding

class HistoryCompanyFragment : Fragment() {

    private var _binding: FragmentHistoryCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel =
            ViewModelProvider(this).get(HistoryCompanyViewModel::class.java)

        _binding = FragmentHistoryCompanyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView
        historyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}