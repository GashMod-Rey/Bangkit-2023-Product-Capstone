package umn.ac.id.myapplication.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.databinding.ItemChatListBinding
import umn.ac.id.myapplication.ui.chat.model.User


class UserAdapter(val data: ArrayList<User>, val onClick: OnClickItem) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private lateinit var binding: ItemChatListBinding

    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_chat_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = data[position]

        holder.itemView.apply {
            binding.titleName.text = user.username
            setOnClickListener {
                onClick.onClick(user)
            }
        }
    }

    override fun getItemCount() = data.size

    interface OnClickItem {
        fun onClick(user: User)
    }

}

