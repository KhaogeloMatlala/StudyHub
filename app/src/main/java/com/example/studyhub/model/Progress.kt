package com.example.studyhub.model

data class Progress(
    val id: Long? = null,
    val points: Int = 0,
    val studyStreak: Int = 0,
    val completedTasks: Int = 0,
    val studyMinutes: Int = 0
)