package com.techknowledgepills.domain.model

data class StressIndicator(
    val id: Int,
    val userId: Int,
    val stressLevel: StressLevel,
    val timestamp: String,
    val notes: String? = null
)

