package com.example.studyhub.api

import com.example.studyhub.model.Task
import com.example.studyhub.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.example.studyhub.model.Subject
import com.example.studyhub.model.Exam
import com.example.studyhub.model.Progress

interface ApiService {

    // ==========================
    // USER
    // ==========================

    @POST("api/users/register")
    fun registerUser(
        @Body user: User
    ): Call<User>

    @POST("api/users/login")
    fun loginUser(
        @Body user: User
    ): Call<User>

    @PUT("api/users/{id}")
    fun updateUser(
        @Path("id") id: Long,
        @Body user: User
    ): Call<User>


    // ==========================
    // TASKS
    // ==========================

    @POST("api/tasks/user/{userId}")
    suspend fun addTask(
        @Path("userId") userId: Long,
        @Body task: Task
    ): Response<Task>

    @GET("api/tasks/user/{userId}")
    suspend fun getTasks(
        @Path("userId") userId: Long
    ): Response<List<Task>>

    @GET("api/tasks/user/{userId}/completed/{completed}")
    suspend fun getTasksByCompletion(
        @Path("userId") userId: Long,
        @Path("completed") completed: Boolean
    ): Response<List<Task>>

    @PUT("api/tasks/{id}/complete")
    suspend fun completeTask(
        @Path("id") id: Long
    ): Response<Task>

    @DELETE("api/tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: Long
    ): Response<Void>

    // ==========================
    // SUBJECTS
    // ==========================

    @POST("api/subjects/user/{userId}")
    suspend fun addSubject(
        @Path("userId") userId: Long,
        @Body subject: Subject
    ): Response<Subject>

    @GET("api/subjects/user/{userId}")
    suspend fun getSubjects(
        @Path("userId") userId: Long
    ): Response<List<Subject>>

    @DELETE("api/subjects/{id}")
    suspend fun deleteSubject(
        @Path("id") id: Long
    ): Response<Void>

    // EXAMS
    @POST("api/exams/user/{userId}")
    suspend fun addExam(
        @Path("userId") userId: Long,
        @Body exam: Exam
    ): Response<Exam>

    @GET("api/exams/user/{userId}")
    suspend fun getExams(
        @Path("userId") userId: Long
    ): Response<List<Exam>>

    @DELETE("api/exams/{id}")
    suspend fun deleteExam(
        @Path("id") id: Long
    ): Response<Void>

    // PROGRESS
    @POST("api/progress/user/{userId}")
    suspend fun createOrUpdateProgress(
        @Path("userId") userId: Long,
        @Body progress: Progress
    ): Response<Progress>

    @GET("api/progress/user/{userId}")
    suspend fun getProgress(
        @Path("userId") userId: Long
    ): Response<Progress>
}