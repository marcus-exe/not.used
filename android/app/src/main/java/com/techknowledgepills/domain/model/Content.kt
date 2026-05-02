package com.techknowledgepills.domain.model

data class Content(
    val id: Int,
    val title: String,
    val type: ContentType,
    val body: String,
    val videoUrl: String? = null,
    val quizData: String? = null,
    val createdAt: String,
    val tags: String? = null
)

