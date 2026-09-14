package com.manhngo.devhubapp.ui.feature.git

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.manhngo.devhubapp.data.local.SecureStorageManager
import com.manhngo.devhubapp.data.remote.dto.GitHubUserDto
import com.manhngo.devhubapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface GitUiState {
    object Unauthenticated : GitUiState
    object Loading : GitUiState
    data class Success(val user: GitHubUserDto) : GitUiState
    data class Error(val message: String) : GitUiState
}

class GitViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository: AuthRepository = AuthRepository(
        secureStorageManager = SecureStorageManager.getInstance(application)
    )

    private val _uiState = MutableStateFlow<GitUiState>(GitUiState.Loading)
    val uiState: StateFlow<GitUiState> = _uiState.asStateFlow()

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (!authRepository.isLoggedIn()) {
            _uiState.value = GitUiState.Unauthenticated
            return
        }
        fetchUserProfile()
    }

    fun handleAuthCode(code: String) {
        viewModelScope.launch {
            _uiState.value = GitUiState.Loading
            val tokenResult = authRepository.exchangeCodeForToken(code)
            tokenResult.fold(
                onSuccess = {
                    fetchUserProfile()
                },
                onFailure = { error ->
                    _uiState.value = GitUiState.Error(error.message ?: "Đăng nhập GitHub thất bại")
                }
            )
        }
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.value = GitUiState.Loading
            val userResult = authRepository.getCurrentUser()
            userResult.fold(
                onSuccess = { user ->
                    _uiState.value = GitUiState.Success(user)
                },
                onFailure = { error ->
                    _uiState.value = GitUiState.Error(error.message ?: "Không thể tải thông tin tài khoản GitHub")
                }
            )
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.value = GitUiState.Unauthenticated
    }
}
