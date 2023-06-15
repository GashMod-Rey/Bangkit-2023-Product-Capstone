package umn.ac.id.myapplication.ui.companypage.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.tensorflow.lite.Interpreter
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.FragmentHomeBinding
import umn.ac.id.myapplication.databinding.FragmentHomeCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.AboutMeActivity
import umn.ac.id.myapplication.ui.chat.adapter.UserAdapter
import umn.ac.id.myapplication.ui.companypage.JobPreferencesActivity
import umn.ac.id.myapplication.ui.data.ListUserAdapter
import umn.ac.id.myapplication.ui.data.UserData
import java.nio.ByteBuffer
import java.nio.ByteOrder

class HomeCompanyFragment : Fragment() {

    private var _binding: FragmentHomeCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var interpreter: Interpreter
    private val list = ArrayList<UserData>()
    private lateinit var userAdapter: ListUserAdapter

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
        userAdapter = ListUserAdapter(list)
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }


        list.addAll(getListUser())
        userAdapter.notifyDataSetChanged()

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

    private fun getListUser(): ArrayList<UserData>{
        val dataName = resources.getStringArray(R.array.data_name)
        val dataSkills = resources.getStringArray(R.array.data_skill)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listUserData = ArrayList<UserData>()
        for (i in dataName.indices){
            val user = UserData(dataName[i], dataSkills[i], dataPhoto.getResourceId(i, -1))
            listUserData.add(user)
        }
        dataPhoto.recycle()
        return listUserData
    }

    private fun showRecyclerList(){

    }
}