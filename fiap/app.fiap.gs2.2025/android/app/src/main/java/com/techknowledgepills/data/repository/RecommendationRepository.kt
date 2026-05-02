package com.techknowledgepills.data.repository

import com.techknowledgepills.data.api.ApiService
import com.techknowledgepills.data.local.TokenManager
import com.techknowledgepills.domain.model.Content
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun getRecommendations(): Result<List<Content>> {
        return try {
            val token = tokenManager.getToken() ?: return Result.failure(Exception("No token"))
            val response = apiService.getRecommendations()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch recommendations: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

