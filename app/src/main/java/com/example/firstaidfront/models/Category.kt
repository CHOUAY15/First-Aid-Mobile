package com.example.firstaidfront.models

import com.example.firstaidfront.R

data class Category(
    val id: Int = 0,
    val iconPath: String = "",
    val title: String = "",
    val goals: String = "",
    val urlYtb: String = "",
    val instructions: List<String> = emptyList(),
    val courses: List<CourseItem> = emptyList(),
    val quizzes: List<Quiz> = emptyList(),
    var iconResId: Int = R.drawable.ic_healthtest // For internal use
)
