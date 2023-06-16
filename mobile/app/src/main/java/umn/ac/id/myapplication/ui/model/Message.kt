package umn.ac.id.myapplication.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    var user: String = "",
    var message: String = "",
    var token: String = "",
    var time: String = "",
) : Parcelable {
    companion object {
        const val USERNAME = "username"
        const val MESSAGE = "message"
        const val TOKEN = "token"
        const val TIME = "time"
    }
}
