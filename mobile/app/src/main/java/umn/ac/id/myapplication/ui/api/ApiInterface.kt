package umn.ac.id.myapplication.ui.api

import android.provider.ContactsContract.Profile
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import umn.ac.id.myapplication.ui.data.*

interface ApiInterface {
    @Multipart
    @POST("uploadCV")
   fun uploadCV(
        @Part pdfFile: MultipartBody.Part
    ): Call<UploadCVResponse>

    @FormUrlEncoded
    @POST("loginApplicant")
    fun postLogin(
        @Field("Username") Username: String,
        @Field("Password") Password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("loginCompany")
    fun postLoginCompany(
        @Field("Username") Username: String,
        @Field("Password") Password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("signupApplicant")
    fun postSignUp(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @POST("signupCompany")
    fun postSignUpCompany(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call <SignUpResponse>

    @FormUrlEncoded
    @POST("setProfileApplicant")
    fun setProfileApplicant(
        @Header("Authorization") token: String,
        @Field("name") Name: String,
        @Field("dateOfBirth") DateOfBirth: String,
        @Field("email") Email: String,
        @Field("language") Language: String,
        @Field("summary") Summary: String,
        @Field("education") Education: String,
        @Field("skills") Skills: String,
        @Field("salaryMin") SalaryMin: Int,
        @Field("location") Location: String,
        @Field("degree") Degree: String,
        @Field("mobilePhone") MobilePhone: String
    ): Call<ProfileApplicantResponse>

    @FormUrlEncoded
    @POST("setProfileCompany")
    fun setProfileCompany(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("summary") summary: String,
        @Field("location") location: String,
        @Field("employee") employee: Int
    ): Call<ProfileApplicantResponse>

    @GET("getProfile")
    fun getProfileApplicant (
        @Header("Authorization") token: String
    ): Call<GetProfileApplicantResponse>

    @GET("getProfileCompany")
    fun getProfileCompany(
        @Header("Authorization") token: String
    ): Call<GetCompanyProfileResponse>

    @FormUrlEncoded
    @POST("offer")
    fun offerProcess(
        @Header("Authorization") token: String,
        @Field("usernameA") usernameA: String,
        @Field("usernameC") usernameC: String
    ): Call<ProcessResponse>

    @FormUrlEncoded
    @POST("offerResponse")
    fun offerApplicant(
        @Header("Authorization") token: String,
        @Field("offer") offer: Boolean
    ): Call<OfferApplicantResponse>

    @FormUrlEncoded
    @POST("status")
    fun status(
        @Header("Authorization") token: String,
        @Field("offer") offer: String
    ): Call<StatusResponse>

    @FormUrlEncoded
    @POST("api/filter")
    fun filterUsers(
        @Header("Authorization") token: String,
        @Field("ageFilter") ageFilter: List<Int>,
        @Field("tolerance") tolerance: Int,
        @Field("skillFilter") skillFilter: List<String>,
        @Field("langFilter")  langFilter: List<String>,
        @Field("salaryFilter") salaryFilter: List<Int>,
        @Field("tol") tol: Int
    ): Call<FilterResponse>





}