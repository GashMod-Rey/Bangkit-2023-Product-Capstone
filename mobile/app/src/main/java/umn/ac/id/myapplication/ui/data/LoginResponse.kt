package umn.ac.id.myapplication.ui.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field: SerializedName("token")
    val token: String? = null,
)
