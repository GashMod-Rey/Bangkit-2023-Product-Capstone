package umn.ac.id.myapplication.ui.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import umn.ac.id.myapplication.ui.data.UploadCVResponse

interface ApiInterface {
    @Multipart
    @POST("upload")
   fun uploadCV(
        @Part pdfFile: MultipartBody.Part
    ): Call<UploadCVResponse>
}