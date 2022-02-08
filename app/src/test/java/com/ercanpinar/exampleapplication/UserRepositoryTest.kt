package com.ercanpinar.exampleapplication

import com.ercanpinar.exampleapplication.data.local.UserDao
import com.ercanpinar.exampleapplication.data.model.User
import com.ercanpinar.exampleapplication.data.remote.UserApi
import com.ercanpinar.exampleapplication.data.util.Result
import com.ercanpinar.exampleapplication.repository.UserRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Mock
    private lateinit var userApi: UserApi

    @Mock
    private lateinit var userDao: UserDao

    @Test
    fun myRepositoryGetUsersSuccessCachedTest() = runBlocking {
        val repository = UserRepository(userApi, userDao)
        val resultList = repository.getUsers()
        resultList.collectIndexed { index, value ->
            if (index == 0) {
                assertTrue(value is Result.Loading)
            } else {
                assertTrue(value is Result.SuccessCached)
            }
        }
    }

    @Test
    fun myRepositoryGetUsersSuccessCachedWhenNullResponseTest() = runBlocking {
        Mockito.doReturn(null)
            .`when`(userApi)
            .getUsers()
        val repository = UserRepository(userApi, userDao)
        val resultList = repository.getUsers()
        resultList.collectIndexed { index, value ->
            if (index == 0) {
                assertTrue(value is Result.Loading)
            } else {
                assertTrue(value is Result.SuccessCached)
            }
        }
    }

    @Test
    fun myRepositoryGetUsersErrorWhenResponseNotCorrectTest() = runBlocking {
        Mockito.doReturn(emptyList<User>())
            .`when`(userApi)
            .getUsers()
        val repository = UserRepository(userApi, userDao)
        val resultList = repository.getUsers()
        resultList.collectIndexed { index, value ->
            if (index == 0) {
                assertTrue(value is Result.Loading)
            } else {
                assertTrue(value is Result.Error)
            }
        }
    }

    @Test
    fun myRepositoryGetUsersSuccessTest() = runBlocking {
        // TODO Success
    }

    @Test
    fun myRepositoryGetUsersErrorTest() = runBlocking {
        // TODO Error
    }

    @Test
    fun myRepositoryGetUserByIdSuccessCachedTest() = runBlocking {
        // TODO SuccessCached
    }

    @Test
    fun myRepositoryGetUserByIdErrorTest() = runBlocking {
        // TODO Error
    }
}