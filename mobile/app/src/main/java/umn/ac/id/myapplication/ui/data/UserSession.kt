package umn.ac.id.myapplication.ui.data

data class UserSession(
    val isLogin: Boolean,
    val token: String,
    val username: String
)
