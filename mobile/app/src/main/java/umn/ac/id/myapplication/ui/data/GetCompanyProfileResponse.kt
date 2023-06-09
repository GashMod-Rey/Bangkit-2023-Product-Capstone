package umn.ac.id.myapplication.ui.data

import com.google.gson.annotations.SerializedName

data class GetCompanyProfileResponse(
    @field: SerializedName("Username")
    val Username: String,

    @field: SerializedName("Password")
    val Password: String,

    @field: SerializedName("Name")
    val Name: String? = null,

    @field: SerializedName("Summary")
    val Summary: String?= null,

    @field: SerializedName("Location")
    val Location: String?= null,

    @field: SerializedName("Employee")
    val Employee: Int? = null,
)
