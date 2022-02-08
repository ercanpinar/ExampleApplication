package com.ercanpinar.exampleapplication.ui

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ercanpinar.exampleapplication.data.local.UserEntity
import com.ercanpinar.exampleapplication.data.model.User
import com.ercanpinar.exampleapplication.data.util.Result
import com.ercanpinar.exampleapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    companion object {
        private const val TAG = "UserViewModel"
    }

    /**
     * User List Data
     */
    private val _userData: MutableLiveData<Result<List<User>>> = MutableLiveData()
    val userData: LiveData<Result<List<User>>>
        get() = _userData

    /**
     * User Details
     */
    private val _userDetail: MutableLiveData<Result<UserEntity>> = MutableLiveData()
    val userDetail: LiveData<Result<UserEntity>>
        get() = _userDetail

    fun loadData(userEvent: UserEvent) {
        viewModelScope.launch {
            when (userEvent) {
                is UserEvent.GetUsersEvent -> {
                    userRepository.getUsers().collectLatest { dataState ->
                        _userData.value = dataState
                    }
                }
                is UserEvent.GetUserByIdEvent -> {
                    userRepository.getUserDbData(userEvent.userId).collectLatest {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                            _userDetail.value = it
                    }
                }
                is UserEvent.None -> {
                    // No action
                }
            }
        }
    }
}

sealed class UserEvent {
    object GetUsersEvent : UserEvent()
    class GetUserByIdEvent(val userId: Int) : UserEvent()
    object None : UserEvent()
}