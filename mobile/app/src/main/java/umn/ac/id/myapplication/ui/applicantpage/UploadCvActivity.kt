package umn.ac.id.myapplication.ui.applicantpage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ActivityUploadCvBinding
import umn.ac.id.myapplication.ui.api.ApiClient
import umn.ac.id.myapplication.ui.api.ApiClient.apiInstance
import umn.ac.id.myapplication.ui.utils.Resource
import umn.ac.id.myapplication.ui.viewmodel.UploadCvViewModel
import java.io.File

class UploadCvActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadCvBinding
    private val PICK_PDF_REQUEST = 1
    private var selectedPdfUri: Uri? = null
    private lateinit var viewModel: UploadCvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadCvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(UploadCvViewModel::class.java)

        binding.buttonCv.setOnClickListener {
            openFilePicker()
        }

        binding.buttonUpload.setOnClickListener {
            uploadFilePdf()
        }

//        viewModel.uploadStatus.observe(this, Observer {
//            uploadSuccessful ->
//            if(uploadSuccessful){
//                Toast.makeText(this@UploadCvActivity, "Upload Successful", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                Toast.makeText(this@UploadCvActivity, "Upload Failed", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        viewModel.errorMessage.observe(this, Observer{
//            errorMessage -> Toast.makeText(this@UploadCvActivity, errorMessage, Toast.LENGTH_SHORT).show()
//        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdf: Uri? = data.data
            if(selectedPdf != null){
                selectedPdfUri = selectedPdf

                val contentResolver = contentResolver
                try {
                    val pdfUri = selectedPdfUri
                    if(pdfUri != null){
                        contentResolver.openInputStream(pdfUri)?.use {
                                inputStream ->
                            Toast.makeText(this@UploadCvActivity, "File successfully stored",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                } catch (e: Exception){
                    Toast.makeText(this@UploadCvActivity, "File access Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openFilePicker(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent, PICK_PDF_REQUEST)
    }

    private fun uploadFilePdf(){
        if(selectedPdfUri != null){
            val contentResolver = contentResolver
            val inputStream = contentResolver.openInputStream(selectedPdfUri!!)
            val file = File(cacheDir, "temp.pdf")
            file.createNewFile()
            file.writeBytes(inputStream!!.readBytes())
            val requestPdfFile = file.asRequestBody("application/pdf".toMediaType())
            val pdfMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "pdf",
                file.name,
                requestPdfFile
            )
            viewModel.uploadFile(pdfMultipart)
            viewModel.pdf.observe(this){
                when(it){
                    is Resource.Success -> {
                        val intent = Intent(this, UploadCvSuccessActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            this, it.message.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


}

