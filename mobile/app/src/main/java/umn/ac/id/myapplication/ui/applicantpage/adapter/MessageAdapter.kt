package umn.ac.id.myapplication.ui.applicantpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umn.ac.id.myapplication.databinding.ItemReceiveMessageBinding
import umn.ac.id.myapplication.databinding.ItemSendMessageBinding
import umn.ac.id.myapplication.ui.applicantpage.LoginActivity
import umn.ac.id.myapplication.ui.model.Message

class MessageAdapter(val dataMessages: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SenderViewHolder(private val senderMessageBinding: ItemSendMessageBinding) :
        RecyclerView.ViewHolder(senderMessageBinding.root) {
        fun bind(message: Message) {
            senderMessageBinding.textMessage.text = message.message
        }
    }

    inner class ReceiverViewHolder(private val receiveMessageBinding: ItemReceiveMessageBinding) :
        RecyclerView.ViewHolder(receiveMessageBinding.root) {
        fun bind(message: Message) {
            receiveMessageBinding.textMessage.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = ItemSendMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SenderViewHolder(binding)
            }
            else -> {
                val binding = ItemReceiveMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ReceiverViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = dataMessages[position]
        when (holder) {
            is SenderViewHolder -> holder.bind(message)
            is ReceiverViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = dataMessages.size

    override fun getItemViewType(position: Int): Int {
        val message = dataMessages[position]
        return when (message.token) {
            LoginActivity.applicant.token -> 0
            else -> 1
        }
    }
}
