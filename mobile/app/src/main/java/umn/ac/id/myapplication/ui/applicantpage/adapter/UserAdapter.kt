package umn.ac.id.myapplication.ui.applicantpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umn.ac.id.myapplication.databinding.ItemChatListBinding
import umn.ac.id.myapplication.ui.model.User

class UserAdapter(val data: ArrayList<User>, val onClick: OnClickItem) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = data[position]

        holder.binding.titleName.text = user.user
        holder.itemView.setOnClickListener {
            onClick.onClick(user)
        }
    }

    override fun getItemCount() = data.size

    interface OnClickItem {
        fun onClick(user: User)
    }
}
