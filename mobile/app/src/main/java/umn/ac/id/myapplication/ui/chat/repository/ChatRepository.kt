package umn.ac.id.myapplication.ui.chat.repository

import umn.ac.id.myapplication.ui.chat.db.ChatDatabase
import umn.ac.id.myapplication.ui.chat.model.Message



class ChatRepository(val db: ChatDatabase) {

    suspend fun insert(message: Message) =
        db.getArticleDao().insert(message)


    fun getAllChat(id: String) = db.getArticleDao().getAllChat(id)

}