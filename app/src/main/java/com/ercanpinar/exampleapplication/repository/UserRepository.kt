package com.ercanpinar.exampleapplication.repository

import com.ercanpinar.exampleapplication.data.local.UserDao
import com.ercanpinar.exampleapplication.data.local.UserEntity
import com.ercanpinar.exampleapplication.data.model.User
import com.ercanpinar.exampleapplication.data.remote.UserApi
import com.ercanpinar.exampleapplication.data.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao
) : Repository {

    suspend fun getUserDbData(userId: Int) = flow {
        try {
            emit(Result.SuccessCached(getUserDetailDbData(userId)))
        } catch (e: Exception) {
            emit(Result.Error("Error fetching User By UserId: $userId"))
        }
    }

    suspend fun getUsers(): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        try {
            // get Data from DB and return, also make api call and then store it in db.
            val userData = userApi.getUsers()
            val isSuccess = !userData.isNullOrEmpty()
            if (isSuccess) {
                saveDataToDb(userData)
                emit(Result.Success(userData))
            } else {
                // Return cached data if there is any
                emit(Result.SuccessCached(emptyList()))
            }
        } catch (e: Exception) {
            emit(Result.Error("Something went wrong. Please try again later."))
        }
    }

    private suspend fun saveDataToDb(userList: List<User>) = with(Dispatchers.IO) {
        userList.map {
            UserEntity(
                id = it.id,
                name = it.name,
                email = it.email,
                phone = it.phone,
                website = it.website
            )

        }.let {
            userDao.insertAllUserItem(it)
        }
    }

    // TODO Implement GetUser cached data usage
    private suspend fun readUserDataFromDb() = with(Dispatchers.IO) {
        val userList = mutableListOf<User>()
        userDao.getUserData().map {
            User(
                id = it.id,
                address = null,
                company = null,
                name = it.name,
                email = it.email,
                phone = it.phone,
                website = it.website,
                username = null
            )
        }.let {
            userList.addAll(it)
        }
        return@with
    }

    private suspend fun getUserDetailDbData(userId: Int) = userDao.getUserById(userId)
}



