package umn.ac.id.myapplication.ui.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import umn.ac.id.myapplication.ui.data.*

interface ApiInterface {
    @Multipart
    @POST("upload")
   fun uploadCV(
        @Part pdfFile: MultipartBody.Part
    ): Call<UploadCVResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("Username") Username: String,
        @Field("Password") Password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("signup")
    fun postSignUp(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<SignUpResponse>

    @GET("setProfileApplicant")
    fun getProfileApplicant(
        @Header("Authorization") token: String
    ): Call<ProfileApplicantResponse>

    @GET("getCV")
    fun getCV (
    ): Call<ProfileApplicantResponse>

}