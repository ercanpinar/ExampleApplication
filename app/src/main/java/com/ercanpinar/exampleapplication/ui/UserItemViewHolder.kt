package com.ercanpinar.exampleapplication.ui

import androidx.recyclerview.widget.RecyclerView
import com.ercanpinar.exampleapplication.clickWithDebounce
import com.ercanpinar.exampleapplication.databinding.ItemUserBinding

class UserItemViewHolder(private val itemUserBinding: ItemUserBinding) :
    RecyclerView.ViewHolder(itemUserBinding.root) {

    companion object {
        private const val TAG = "UserItemViewHolder"
    }

    fun bind(userCardVO: UserCardVO) = with(itemUserBinding) {
        val name = userCardVO.data.name
        val phone = userCardVO.data.phone
        nameTextView.text = name
        phoneTextView.text = phone

        // A11y
        userItemCardView.contentDescription =
            "$name, ${phoneTitleTextView.text} $phone"

        itemUserBinding.root.clickWithDebounce {
            userCardVO.onClickListener(userCardVO)
        }
    }
}