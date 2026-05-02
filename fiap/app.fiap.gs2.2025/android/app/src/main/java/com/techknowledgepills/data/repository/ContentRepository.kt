package com.techknowledgepills.data.repository

import com.techknowledgepills.data.api.ApiService
import com.techknowledgepills.data.local.TokenManager
import com.techknowledgepills.domain.model.Content
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun getAllContent(): Result<List<Content>> {
        return try {
            val token = tokenManager.getToken() ?: return Result.failure(Exception("No token. Please log in."))
            val response = apiService.getAllContent()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch content: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getContentById(id: Int): Result<Content> {
        return try {
            val token = tokenManager.getToken() ?: return Result.failure(Exception("No token. Please log in."))
            val response = apiService.getContentById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch content: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getContentByType(type: Int): Result<List<Content>> {
        return try {
            val token = tokenManager.getToken() ?: return Result.failure(Exception("No token. Please log in."))
            val response = apiService.getContentByType(type)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch content: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

