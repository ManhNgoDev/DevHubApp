package com.manhngo.devhubapp.data.repository

import com.manhngo.devhubapp.data.local.SecureStorageManager
import com.manhngo.devhubapp.data.remote.api.GitHubApiService
import com.manhngo.devhubapp.data.remote.dto.GitHubUserDto
import com.manhngo.devhubapp.util.constant.EnvConfig
import com.manhngo.devhubapp.util.network.RetrofitClient

class AuthRepository(
    private val apiService: GitHubApiService = RetrofitClient.createService(),
    private val secureStorageManager: SecureStorageManager
) {

    suspend fun exchangeCodeForToken(code: String): Result<String> {
        return try {
            val response = apiService.getAccessToken(
                clientId = EnvConfig.GITHUB_CLIENT_ID,
                clientSecret = EnvConfig.GITHUB_CLIENT_SECRET,
                code = code,
                redirectUri = null
            )

            val token = response.accessToken
            if (!token.isNullOrBlank()) {
                secureStorageManager.saveAuthToken(token)
                Result.success(token)
            } else {
                val errorMsg = response.errorDescription ?: response.error ?: "Không lấy được access token từ GitHub"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<GitHubUserDto> {
        val token = secureStorageManager.getAuthToken()
            ?: return Result.failure(Exception("Chưa đăng nhập"))

        return try {
            val user = apiService.getUserProfile("Bearer $token")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isLoggedIn(): Boolean {
        return !secureStorageManager.getAuthToken().isNullOrBlank()
    }

    fun logout() {
        secureStorageManager.clearAuthToken()
    }
}
