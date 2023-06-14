package umn.ac.id.myapplication.ui.companypage.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.tensorflow.lite.Interpreter
import umn.ac.id.myapplication.databinding.FragmentHomeBinding
import umn.ac.id.myapplication.databinding.FragmentHomeCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.AboutMeActivity
import umn.ac.id.myapplication.ui.companypage.JobPreferencesActivity
import java.nio.ByteBuffer
import java.nio.ByteOrder

class HomeCompanyFragment : Fragment() {

    private var _binding: FragmentHomeCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var interpreter: Interpreter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeCompanyViewModel::class.java)

        _binding = FragmentHomeCompanyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchFilter.setOnClickListener {
            val intent = Intent(requireContext(), JobPreferencesActivity::class.java)
            startActivity(intent)
        }
//
//        val assetManager = requireContext().assets
//        val tflitePath = "model.tflite"
//        val interpreter = Interpreter(requireContext().assets.openFd(tflitePath).fileDescriptor)
//
//        val inputSize = 5
//        val inputChannels = 4
//
//        val inputBuffer = ByteBuffer.allocateDirect(inputSize * inputSize * inputChannels * Float.SIZE_BYTES).apply {
//            order(ByteOrder.nativeOrder())
//        }
//        val floatBuffer = inputBuffer.asFloatBuffer()
//
//        val input = arrayOf(
//            floatArrayOf(0.33f, 0.33f, 0f, 0f),
//            floatArrayOf(0f, 0.33f, 0f, 0f),
//            floatArrayOf(0f, 0f, 0f, 0f),
//            floatArrayOf(0f, 0f, 0f, 0f)
//        )
//
//        input.forEach {
//            floatBuffer.put(it)
//        }
//
//        val output = Array(desiredOutputSize){
//            FloatArray(outputChannels)
//        }
//        interpreter.run(inputBuffer, output)
//
//        val flatScores = output.flatten().toFloatArray()
//        buffer.put(flatScores)
//
//        val indices = buffer.array().indices.sorted
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}