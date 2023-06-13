//package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import umn.ac.id.myapplication.ui.chat.db.ChatDatabase
//import umn.ac.id.myapplication.ui.chat.repository.ChatRepository
//
//class ChatViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val chatRepository =
//        ChatRepository(ChatDatabase(application.applicationContext))
//    fun insert(message: Message) = viewModelScope.launch {
//        chatRepository.insert(message)
//    }
//    fun getAllChat(id: String) = chatRepository.getAllChat(id)
//
//}