package umn.ac.id.myapplication.ui.chat.db

import  androidx.lifecycle.LiveData
import androidx.room.*
import umn.ac.id.myapplication.ui.chat.model.Message

@Dao
interface ChatDao {

    @Insert
    suspend fun insert(message: umn.ac.id.myapplication.ui.chat.model.Message)

    @Query("SELECT * FROM MessageTable WHERE idUser LIKE :id ORDER BY id DESC")
    fun getAllChat(id: String): LiveData<List<Message>>
}