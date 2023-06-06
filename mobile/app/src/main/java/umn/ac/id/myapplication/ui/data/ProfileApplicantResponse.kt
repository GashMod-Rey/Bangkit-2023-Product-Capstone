package umn.ac.id.myapplication.ui.data

import com.google.gson.annotations.SerializedName

data class ProfileApplicantResponse(
    @field: SerializedName("name")
    val name: String,

    @field: SerializedName("dateOfBirth")
    val dateOfBirth: Int,

    @field: SerializedName("email")
    val email: String,

    @field: SerializedName("language")
    val language: String,

    @field: SerializedName("summary")
    val summary: String,

    @field: SerializedName("education")
    val education: String,

    @field: SerializedName("skills")
    val skills: String,

    @field: SerializedName("salaryMin")
    val salaryMin: Int,

    @field: SerializedName("location")
    val location: String,

    @field: SerializedName("degree")
    val degree: String,

    @field: SerializedName("mobilePhone")
    val mobilePhone: String,

    @field: SerializedName("openToWork")
    val openToWork: Boolean
)
