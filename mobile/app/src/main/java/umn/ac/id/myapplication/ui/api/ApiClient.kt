package umn.ac.id.myapplication.ui.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
//private const val BASEURL = "http://192.168.100.12:8000/" //GianURL
private const val BASEURL = "http://192.168.18.9:8000/" //NeheURL
//    private const val BASEURL = "http://192.168.43.20:8000/"
//private const val BASEURL = "https://backend-dot-capstone-project-hirehub.et.r.appspot.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance = retrofit.create(ApiInterface::class.java)
}