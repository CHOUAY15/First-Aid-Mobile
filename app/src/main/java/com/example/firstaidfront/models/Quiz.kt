package com.example.firstaidfront.models

data class Quiz(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)