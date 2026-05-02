package com.techknowledgepills.domain.model

data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val expiresAt: String,
    val userId: Int,
    val email: String
)

