package com.example.firstaidfront.models

data class TestQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    var selectedAnswer: Int? = null
)