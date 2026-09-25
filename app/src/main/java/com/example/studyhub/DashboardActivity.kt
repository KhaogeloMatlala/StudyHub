package com.example.studyhub

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studyhub.model.Progress
import com.example.studyhub.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvTasksCard: TextView
    private lateinit var tvStudyCard: TextView
    private lateinit var tvPoints: TextView
    private lateinit var tvStreak: TextView

    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        userId = intent.getLongExtra("USER_ID", -1L)

        tvTasksCard = findViewById(R.id.tvTasksCard)
        tvStudyCard = findViewById(R.id.tvStudyCard)
        tvPoints = findViewById(R.id.tvPoints)
        tvStreak = findViewById(R.id.tvStreak)

        setupNavigation()

        loadProgress()
    }

    override fun onResume() {
        super.onResume()

        if (userId != -1L) {
            loadProgress()
        }
    }

    private fun setupNavigation() {

        findViewById<android.view.View>(R.id.btnTasks)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    TasksActivity::class.java
                )

                intent.putExtra("USER_ID", userId)

                startActivity(intent)
            }

        findViewById<android.view.View>(R.id.btnSubjects)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    SubjectsActivity::class.java
                )

                intent.putExtra("USER_ID", userId)

                startActivity(intent)
            }

        findViewById<android.view.View>(R.id.btnExams)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    ExamActivity::class.java
                )

                intent.putExtra("USER_ID", userId)

                startActivity(intent)
            }

        findViewById<android.view.View>(R.id.btnFocus)
            .setOnClickListener {

                startActivity(
                    Intent(
                        this,
                        FocusActivity::class.java
                    )
                )
            }

        findViewById<android.view.View>(R.id.btnProfile)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    ProfileActivity::class.java
                )

                intent.putExtra("USER_ID", userId)

                startActivity(intent)
            }
    }

    private fun loadProgress() {

        if (userId == -1L) return

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.getProgress(userId)

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {

                        val progress =
                            response.body()

                        if (progress != null) {

                            displayProgress(progress)

                        } else {

                            displayProgress(
                                Progress()
                            )
                        }

                    } else {

                        displayProgress(
                            Progress()
                        )
                    }
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    displayProgress(
                        Progress()
                    )
                }
            }
        }
    }

    private fun displayProgress(progress: Progress) {

        tvTasksCard.text =
            "Tasks\n${progress.completedTasks} completed"

        tvStudyCard.text =
            "Study Time\n${progress.studyMinutes} minutes"

        tvPoints.text =
            "Points: ${progress.points}"

        tvStreak.text =
            "Study Streak: ${progress.studyStreak} days"
    }
}