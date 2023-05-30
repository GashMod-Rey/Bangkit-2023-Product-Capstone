package umn.ac.id.myapplication.ui.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @Multipart
    @POST("upload")
    suspend fun uploadCV(
        @Part pdf: MultipartBody.Part
    ): Response<ResponseBody>
}