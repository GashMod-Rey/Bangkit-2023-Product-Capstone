package umn.ac.id.myapplication.ui.data

import com.google.gson.annotations.SerializedName

data class UploadCVResponse(
    @field: SerializedName("error")
    val error: Boolean,

    @field: SerializedName("message")
    val message: String
)
