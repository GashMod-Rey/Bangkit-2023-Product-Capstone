package umn.ac.id.myapplication.ui.applicantpage.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var username: String = "",
    var image: String = "",
    var password: String = "",
    var isOnline: Boolean = false
) : Parcelable {

    companion object {
        const val NAME = "name"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val ID = "id"
        const val IMAGE = "image"
        const val IS_ONLINE = "isOnline"
    }

}