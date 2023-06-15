package umn.ac.id.myapplication.ui.data

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class UserData(
    val name: String,
    val skills: String,
    val photo: Int
): Parcelable
