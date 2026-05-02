package com.techknowledgepills.data.repository

import com.techknowledgepills.data.api.ApiService
import com.techknowledgepills.data.api.LoginRequest
import com.techknowledgepills.data.api.RegisterRequest
import com.techknowledgepills.data.local.TokenManager
import com.techknowledgepills.domain.model.AuthResponse
import com.techknowledgepills.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun register(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.register(RegisterRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveToken(authResponse.token)
                tokenManager.saveRefreshToken(authResponse.refreshToken)
                tokenManager.saveUserId(authResponse.userId)
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Registration failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveToken(authResponse.token)
                tokenManager.saveRefreshToken(authResponse.refreshToken)
                tokenManager.saveUserId(authResponse.userId)
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<User> {
        return try {
            val token = tokenManager.getToken()
            if (token == null) {
                return Result.failure(Exception("No token available"))
            }
            val response = apiService.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get user: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        tokenManager.clearTokens()
    }

    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }
}

