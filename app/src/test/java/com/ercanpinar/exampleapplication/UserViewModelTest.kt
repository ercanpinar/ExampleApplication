package com.ercanpinar.exampleapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ercanpinar.exampleapplication.data.local.UserDao
import com.ercanpinar.exampleapplication.data.model.User
import com.ercanpinar.exampleapplication.data.remote.UserApi
import com.ercanpinar.exampleapplication.data.util.Result
import com.ercanpinar.exampleapplication.repository.UserRepository
import com.ercanpinar.exampleapplication.ui.UserEvent
import com.ercanpinar.exampleapplication.ui.UserViewModel
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var userApi: UserApi

    @Mock
    private lateinit var userDao: UserDao

    @Test
    fun viewModelLoadingCheck() = runBlocking {
        doReturn(emptyList<User>())
            .`when`(userApi)
            .getUsers()
        val repository = UserRepository(userApi, userDao)
        val model = UserViewModel(repository)
        model.userData.observeForever {
            assertNotNull(it)
            assertTrue(it is Result.Loading)
        }
        model.loadData(UserEvent.GetUsersEvent)
    }

    @Test
    fun viewModelSuccessCheck() = runBlocking {
        // TODO Implement success response steps
    }

    @Test
    fun viewModelSuccessCachedCheck() = runBlocking {
        // TODO Implement success cached response steps
    }

    @Test
    fun viewModelErrorCheck() = runBlocking {
        // TODO Implement error response steps
    }
}