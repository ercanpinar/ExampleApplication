package com.ercanpinar.exampleapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ercanpinar.exampleapplication.R
import com.ercanpinar.exampleapplication.data.local.user.UserEntity
import com.ercanpinar.exampleapplication.data.util.Result
import com.ercanpinar.exampleapplication.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    companion object {
        private const val INVALID_USER_ID = -1
        private const val TAG = "UserDetailFragment"
    }

    private lateinit var binding: FragmentUserDetailBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userViewModel.loadData(
            UserEvent.GetUserByIdEvent(
                arguments?.getInt(getString(R.string.arg_user_id)) ?: INVALID_USER_ID
            )
        )
        binding = FragmentUserDetailBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserDetailData()
    }

    private fun observeUserDetailData() {
        userViewModel.userDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.SuccessCached -> {
                    displayData(result.cacheData)
                }
                is Result.Error -> {
                    displayError(message = result.message)
                }
                else -> Unit
            }
        }
    }

    private fun displayError(message: String?) {
        val errorMessage = message ?: resources.getString(R.string.error_something_went_wrong_message)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        Log.d(TAG, "Error")
    }

    private fun displayData(userEntity: UserEntity) = with(binding) {
        userNameTextView.text = userEntity.name
        phoneTextView.text = userEntity.phone
        emailTextView.text = userEntity.email
        websiteTextView.text = userEntity.website
        Log.d(TAG, "Success")
    }
}