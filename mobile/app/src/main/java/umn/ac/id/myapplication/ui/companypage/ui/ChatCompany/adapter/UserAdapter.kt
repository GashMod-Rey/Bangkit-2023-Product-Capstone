package umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.adapter

import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.model.User
import umn.ac.id.myapplication.ui.companypage.ui.ChatCompany.other.decodeImage


class UserAdapter :
    BaseUserAdapter(R.layout.item_list_users) {
    override val differ = AsyncListDiffer(this, diffCallback)
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = dataList[position] as User
//        holder.itemView.apply {
//            image.setImageBitmap(decodeImage(user.image))
//            username.text = user.name
//            isOnline.setCardBackgroundColor(
//                ResourcesCompat.getColor(
//                    context.resources,
//                    if (user.isOnline) R.color.greenSuccess else R.color.greyCancelled,
//                    null
//                )
//            )
//            setOnClickListener {
//                onItemClickListener?.let { click ->
//                    click(user, false)
//                }
//            }
//        }
    }
}