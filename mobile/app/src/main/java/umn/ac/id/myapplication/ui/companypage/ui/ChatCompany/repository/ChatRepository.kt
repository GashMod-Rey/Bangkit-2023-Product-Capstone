package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.repository

import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.db.ChatDatabase
import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.Message

class ChatRepository(val db: ChatDatabase) {

    suspend fun insert(message: Message) =
        db.getArticleDao().insert(message)


    fun getAllChat(id: String) = db.getArticleDao().getAllChat(id)

}