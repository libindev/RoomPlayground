package com.example.roomplayground.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomplayground.model.UserInfo
import com.example.roomplayground.repository.UserRepository
import com.example.roomplayground.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class UserDataFetchState {
    IDLE, LOADING, ERROR,
}
data class HomeUiState(
    val userDataFetchState: UserDataFetchState = UserDataFetchState.IDLE,
    val userData: List<UserInfo> = listOf()
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _viewModelState = MutableStateFlow(HomeUiState())
    val viewModelState: StateFlow<HomeUiState> = _viewModelState
    init {
        viewModelScope.launch {
            _viewModelState.emit(_viewModelState.value.copy(userData = listOf(), userDataFetchState = UserDataFetchState.LOADING))
            fetchUserData()
        }
    }

    private suspend fun fetchUserData() {
        userRepository.getUserDetails().collect {
            when (it) {
                is State.Failed -> {
                    _viewModelState.emit(
                        _viewModelState.value.copy(
                            userData = listOf(),
                            userDataFetchState = UserDataFetchState.ERROR
                        )
                    )
                }
                is State.Success -> {
                    _viewModelState.emit(_viewModelState.value.copy(userData = it.data))
                }
            }
        }
    }

    fun saveUserData(name: String, email: String, phoneNo: String) {
        viewModelScope.launch {
            userRepository.saveUserData(name, email, phoneNo).collect { its ->
                when (its) {
                    is State.Failed -> {
                        _viewModelState.emit(
                            _viewModelState.value.copy(
                                userData = listOf(),
                                userDataFetchState = UserDataFetchState.ERROR
                            )
                        )
                    }
                    is State.Success -> {
                        fetchUserData()
                    }
                }
            }
        }
    }
}
