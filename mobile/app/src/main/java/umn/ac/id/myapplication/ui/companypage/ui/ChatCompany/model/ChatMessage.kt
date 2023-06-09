package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MessageTable")
data class ChatMessage(
    var username: String = "",
    var message: String = "",
    var imageUser: String = "",
    var idS: String = "",
    var isTyping: Boolean = false,
    var idUser: String = "",
    var time: String = "",
    var replyTo: String = "",
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0
)
