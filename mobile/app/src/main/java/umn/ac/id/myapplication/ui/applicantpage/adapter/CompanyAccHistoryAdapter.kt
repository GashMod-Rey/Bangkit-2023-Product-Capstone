package umn.ac.id.myapplication.ui.applicantpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umn.ac.id.myapplication.databinding.ItemDeclineListBinding
import umn.ac.id.myapplication.databinding.ItemSuccessListBinding
import umn.ac.id.myapplication.ui.data.HistoryDataResponse

class CompanyAccHistoryAdapter(val data: ArrayList<HistoryDataResponse>, val onClick: OnClickItem) :
    RecyclerView.Adapter<CompanyAccHistoryAdapter.MyViewHolder>(){

    inner class MyViewHolder(val binding: ItemSuccessListBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemSuccessListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val historyData = data[position]

        holder.binding.titleName.text = historyData.UsernameC
        holder.itemView.setOnClickListener {
            onClick.onClick(historyData)
        }
    }

    override fun getItemCount() = data.size

    interface OnClickItem {
        fun onClick(historyDataResponse: HistoryDataResponse)
    }
}