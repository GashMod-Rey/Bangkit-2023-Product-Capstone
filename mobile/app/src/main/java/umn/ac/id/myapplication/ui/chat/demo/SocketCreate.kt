package umn.ac.id.myapplication.ui.chat.demo

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket

class SocketCreate {

    private var mSocket: Socket? = IO.socket("http://192.168.100.12:8000/")

    fun getSocket(): Socket? {
        return mSocket
    }

}