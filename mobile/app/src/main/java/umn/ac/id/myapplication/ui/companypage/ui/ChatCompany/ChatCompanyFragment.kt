package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import umn.ac.id.myapplication.databinding.FragmentChatCompanyBinding
class ChatCompanyFragment : Fragment() {

    private var _binding: FragmentChatCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ChatCompanyViewModel::class.java)

        _binding = FragmentChatCompanyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.TextTest
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}