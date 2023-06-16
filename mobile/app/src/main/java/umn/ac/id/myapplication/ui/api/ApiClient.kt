package umn.ac.id.myapplication.ui.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

//object ApiClient {
//private const val BASEURL = "http://192.168.100.12:8000/" //GianURL
//private const val BASEURL = "http://192.168.18.9:8000/" //NeheURL
//    private const val BASEURL = "http://192.168.43.20:8000/"
//    val retrofit = Retrofit.Builder()
//        .baseUrl(BASEURL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val apiInstance = retrofit.create(ApiInterface::class.java)
//}



object ApiClient {
//    private const val BASEURL = "http://192.168.43.20:8000/"
//private const val BASEURL = "http://192.168.100.12:8000/" //GianURL
    private const val BASEURL = "https://backend-dot-capstone-project-hirehub.et.r.appspot.com"
    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiInstance = retrofit.create(ApiInterface::class.java)
}