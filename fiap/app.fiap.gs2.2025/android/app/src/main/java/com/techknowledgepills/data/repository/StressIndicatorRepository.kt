package com.techknowledgepills.data.repository

import com.techknowledgepills.data.api.ApiService
import com.techknowledgepills.data.local.TokenManager
import com.techknowledgepills.domain.model.StressIndicator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StressIndicatorRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun getStressIndicators(): Result<List<StressIndicator>> {
        return try {
            val token = tokenManager.getToken() ?: return Result.failure(Exception("No token"))
            val response = apiService.getStressIndicators()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch stress indicators: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLatestStressIndicator(): Result<StressIndicator> {
        return try {
            val token = tokenManager.getToken() ?: return Result.failure(Exception("No token"))
            val response = apiService.getLatestStressIndicator()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch latest stress indicator: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun generateMockData(count: Int = 30): Result<List<StressIndicator>> {
        return try {
            val token = tokenManager.getToken() ?: return Result.failure(Exception("No token"))
            val response = apiService.generateMockData(count)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to generate mock data: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

