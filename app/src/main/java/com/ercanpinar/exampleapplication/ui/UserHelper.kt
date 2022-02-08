package com.ercanpinar.exampleapplication.ui

import com.ercanpinar.exampleapplication.data.model.User

object UserHelper {

    fun getUserListCards(data: List<User>, cardClickListener: (UserCardVO) -> Unit) =
        data.map { UserCardVO(data = it, onClickListener = cardClickListener) }

}