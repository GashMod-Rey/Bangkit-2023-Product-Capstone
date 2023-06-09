package umn.ac.id.myapplication.ui.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umn.ac.id.myapplication.R

class ListUserAdapter(private val listUser: ArrayList<UserData>):
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
            this.onItemClickCallback = onItemClickCallback
        }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val photo : ImageView = itemView.findViewById(R.id.photo)
        val name : TextView = itemView.findViewById(R.id.title_name)
        val skills : TextView = itemView.findViewById(R.id.chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, skills, photo) = listUser[position]
        holder.photo.setImageResource(photo)
        holder.name.text = name
        holder.skills.text = skills

        holder.itemView.setOnClickListener{
        }
    }

    override fun getItemCount(): Int = listUser.size
    interface OnItemClickCallback{
        fun onItemClicked(data: UserData)
    }


}