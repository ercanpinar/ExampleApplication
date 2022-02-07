package com.ercanpinar.exampleapplication.data.model.user

import kotlinx.serialization.Serializable


class UserResponse : ArrayList<User>()

@Serializable
data class User(
    val id: Int,
    val address: Address?,
    val company: Company?,
    val email: String,
    val name: String,
    val phone: String,
    val username: String?,
    val website: String
)

@Serializable
data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)

@Serializable
data class Company(
    val bs: String,
    val catchPhrase: String,
    val name: String
)

@Serializable
data class Geo(
    val lat: String,
    val lng: String
)