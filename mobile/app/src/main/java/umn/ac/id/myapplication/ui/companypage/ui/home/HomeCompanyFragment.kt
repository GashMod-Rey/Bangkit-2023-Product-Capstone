package umn.ac.id.myapplication.ui.companypage.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.FragmentHomeBinding
import umn.ac.id.myapplication.databinding.FragmentHomeCompanyBinding
import umn.ac.id.myapplication.ui.applicantpage.AboutMeActivity
import umn.ac.id.myapplication.ui.companypage.adapter.UserAdapter
import umn.ac.id.myapplication.ui.companypage.JobPreferencesActivity
import umn.ac.id.myapplication.ui.data.ListUserAdapter
import umn.ac.id.myapplication.ui.data.UserData
import umn.ac.id.myapplication.ui.data.UserDataResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.util.Collections.emptyList


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

        val fil_user = arguments?.getString("fil_user")
        var userDataList: List<UserDataResponse> = emptyList()
        var sortedUserDataList: List<UserDataResponse> = emptyList()
        var userDataScore = ArrayList<Float>()

        if(fil_user != null) {
            userDataList = Gson().fromJson(fil_user, object : TypeToken<List<UserDataResponse>>() {}.type)

            var scoreMatrix = ArrayList<List<Float>>()

            for(i in userDataList) {
                scoreMatrix.add(i.Score)
            }

            val assetManager = requireContext().assets
            val modelPath = "model.tflite"
            val fileDescriptor = assetManager.openFd(modelPath)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            val interpreter = Interpreter(fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength))

            val inputSize = 1
            val inputChannels = 4

            val inputBuffer = ByteBuffer.allocateDirect(inputSize * inputChannels * Float.SIZE_BYTES).apply {
                order(ByteOrder.nativeOrder())
            }

            for(row in scoreMatrix) {
                val floatBuffer = inputBuffer.asFloatBuffer()
                for (item in row) {
                    floatBuffer.put(item)
                }

                val outputSize = 1
                val outputChannels = 1

                val output = Array(outputSize){
                    FloatArray(outputChannels)
                }

                interpreter.run(inputBuffer, output)

                for (row in output) {
                    for (value in row) {
                        userDataScore.add(value)
                    }
                }
            }

            Log.d("Hi", userDataScore.toString())
            val indexedValues = userDataScore.withIndex()
            val sortedIndices = indexedValues.sortedByDescending {it.value}.map {it.index}
            sortedUserDataList = sortedIndices.map { userDataList[it] }

        }

        list.addAll(getListUser(userDataList))
        userAdapter.notifyDataSetChanged()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getListUser(userDataList: List<UserDataResponse>): ArrayList<UserData>{
        val listUserData = ArrayList<UserData>()
        if (userDataList.isEmpty()) {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataSkills = resources.getStringArray(R.array.data_skill)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            for (i in dataName.indices){
                val user = UserData(dataName[i], dataSkills[i], dataPhoto.getResourceId(i, -1))
                listUserData.add(user)
            }
            dataPhoto.recycle()
        }
        else
        {
            for (i in userDataList){
                val user = UserData(i.Name!!, i.Skills!!, R.drawable.ic_person)
                listUserData.add(user)
            }
        }
        return listUserData
    }

    private fun showRecyclerList(){

    }
}