package com.techknowledgepills.data.api

import com.techknowledgepills.domain.model.AuthResponse
import com.techknowledgepills.domain.model.Content
import com.techknowledgepills.domain.model.StressIndicator
import com.techknowledgepills.domain.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Auth
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("api/auth/me")
    suspend fun getCurrentUser(): Response<User>

    // Content
    @GET("api/content")
    suspend fun getAllContent(): Response<List<Content>>

    @GET("api/content/{id}")
    suspend fun getContentById(
        @Path("id") id: Int
    ): Response<Content>

    @GET("api/content/type/{type}")
    suspend fun getContentByType(
        @Path("type") type: Int // 1=Article, 2=Video, 3=Quiz
    ): Response<List<Content>>

    // Stress Indicators
    @GET("api/stressindicator")
    suspend fun getStressIndicators(): Response<List<StressIndicator>>

    @GET("api/stressindicator/latest")
    suspend fun getLatestStressIndicator(): Response<StressIndicator>

    @POST("api/stressindicator/generate-mock")
    suspend fun generateMockData(
        @Query("count") count: Int = 30
    ): Response<List<StressIndicator>>

    @POST("api/stressindicator")
    suspend fun createStressIndicator(
        @Body indicator: StressIndicator
    ): Response<StressIndicator>

    // Recommendations
    @GET("api/recommendation")
    suspend fun getRecommendations(): Response<List<Content>>
}

data class RegisterRequest(val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)

