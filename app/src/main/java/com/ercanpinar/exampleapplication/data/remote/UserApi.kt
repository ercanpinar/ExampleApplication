package com.ercanpinar.exampleapplication.data.remote

import com.ercanpinar.exampleapplication.data.model.user.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun getUsers(): UserResponse

}