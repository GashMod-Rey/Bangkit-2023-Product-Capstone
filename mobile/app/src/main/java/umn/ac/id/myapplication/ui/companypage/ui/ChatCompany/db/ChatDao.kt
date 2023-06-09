package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.db

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.room.*



@Dao
interface ChatDao {

    @Insert
    suspend fun insert(message: umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.Message)

    @Query("SELECT * FROM MessageTable WHERE idUser LIKE :id ORDER BY id DESC")
    fun getAllChat(id: String): LiveData<List<umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.Message>>
}
