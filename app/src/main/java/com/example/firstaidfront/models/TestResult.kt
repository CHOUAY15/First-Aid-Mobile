package com.example.firstaidfront.models
data class TestResult(
    val participantId: Int,
    val trainingId: Int,
    val userAnswers: List<ParticipantAnswer>,
    val score: Float,
    val submissionDate: String,
    val passed: Boolean
)