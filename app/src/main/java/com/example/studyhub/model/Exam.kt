package com.example.studyhub.model

data class Exam(
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    val examDate: String,
    val venue: String? = null,
    val subjectId: Long? = null
)