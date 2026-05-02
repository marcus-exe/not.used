package com.techknowledgepills.domain.model

data class User(
    val id: Int,
    val email: String,
    val createdAt: String? = null,
    val lastLogin: String? = null
)

