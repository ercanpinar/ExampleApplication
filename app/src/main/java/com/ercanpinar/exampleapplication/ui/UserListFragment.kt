package com.ercanpinar.exampleapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ercanpinar.exampleapplication.R
import com.ercanpinar.exampleapplication.data.model.user.User
import com.ercanpinar.exampleapplication.data.util.Result
import com.ercanpinar.exampleapplication.databinding.FragmentUserListBinding
import com.ercanpinar.exampleapplication.navigateWithAnim
import com.ercanpinar.exampleapplication.ui.UserHelper.getUserListCards
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    companion object {
        private const val TAG = "UserListFragment"
    }

    private lateinit var binding: FragmentUserListBinding

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.loadData(UserEvent.GetUsersEvent)
        observeUserData()
    }

    private fun observeUserData() {
        userViewModel.userData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    displayLoading()
                }
                is Result.Success<List<User>> -> {
                    displayData(result.data)
                }
                is Result.SuccessCached -> {
                    displayData(result.cacheData, true)
                }
                is Result.Error -> {
                    displayError(result.message)
                }
                else -> displayError("Something went wrong")
            }
        }
    }

    private fun displayError(message: String?) {
        binding.progressBar.hide()
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayData(data: List<User>, isFromCache: Boolean = false) = with(binding) {
        progressBar.hide()
        if (isFromCache) {
            Toast.makeText(context, "Cached Data", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Success Fresh Data", Toast.LENGTH_LONG).show()
        }

        userListRecyclerView.visibility = View.VISIBLE
        userAdapter = UserAdapter(getUserListCards(data, ::cardClickListener))
        userListRecyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun displayLoading() = with(binding) {
        progressBar.show()
        userListRecyclerView.visibility = View.GONE
    }

    private fun cardClickListener(userCardVO: UserCardVO) {
        Log.d(TAG, "cardClickListener: User Clicked: ${userCardVO.data.name}")
        findNavController().navigateWithAnim(
            R.id.action_userListFragment_to_userDetailFragment,
            bundleOf(getString(R.string.arg_user_id) to userCardVO.data.id)
        )
    }
}