package com.techknowledgepills.domain.model

data class Quiz(
    val questions: List<QuizQuestion>
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correct: Int,
    val explanation: String
)

