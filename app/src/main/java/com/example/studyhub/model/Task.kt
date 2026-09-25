package com.example.studyhub.model

data class Task(
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    val priority: String,
    val completed: Boolean = false,
    val dueDate: String? = null
)