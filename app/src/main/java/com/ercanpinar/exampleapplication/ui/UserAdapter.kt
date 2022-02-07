package com.ercanpinar.exampleapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ercanpinar.exampleapplication.data.model.user.User
import com.ercanpinar.exampleapplication.databinding.ItemUserBinding

class UserAdapter(
    var userList: List<UserCardVO>
) : RecyclerView.Adapter<UserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val baseBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserItemViewHolder(baseBinding)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}

data class UserCardVO(
    val data: User,
    val onClickListener: (UserCardVO) -> Unit
)