package com.example.studyhub

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studyhub.model.Exam
import com.example.studyhub.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamActivity : AppCompatActivity() {

    private lateinit var etExamTitle: EditText
    private lateinit var etExamDescription: EditText
    private lateinit var etExamDate: EditText
    private lateinit var etExamVenue: EditText
    private lateinit var btnAddExam: Button
    private lateinit var examsProgressBar: ProgressBar
    private lateinit var examContainer: LinearLayout

    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exams)

        userId = intent.getLongExtra("USER_ID", -1L)

        etExamTitle = findViewById(R.id.etExamTitle)
        etExamDescription = findViewById(R.id.etExamDescription)
        etExamDate = findViewById(R.id.etExamDate)
        etExamVenue = findViewById(R.id.etExamVenue)
        btnAddExam = findViewById(R.id.btnAddExam)
        examsProgressBar = findViewById(R.id.examsProgressBar)
        examContainer = findViewById(R.id.examContainer)

        btnAddExam.setOnClickListener {
            addExam()
        }

        // loadExams()
    }

    private fun addExam() {

        val title = etExamTitle.text.toString().trim()
        val description = etExamDescription.text.toString().trim()
        val examDate = etExamDate.text.toString().trim()
        val venue = etExamVenue.text.toString().trim()

        if (title.isEmpty()) {
            etExamTitle.error = "Exam title is required"
            etExamTitle.requestFocus()
            return
        }

        if (examDate.isEmpty()) {
            etExamDate.error = "Exam date is required"
            etExamDate.requestFocus()
            return
        }

        if (!examDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
            etExamDate.error = "Use YYYY-MM-DD"
            etExamDate.requestFocus()
            return
        }

        if (userId == -1L) {
            Toast.makeText(
                this,
                "User session not found.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        setLoading(true)

        val exam = Exam(
            title = title,
            description = description.ifEmpty { null },
            examDate = examDate,
            venue = venue.ifEmpty { null }
        )

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.addExam(userId, exam)

                withContext(Dispatchers.Main) {

                    setLoading(false)

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@ExamActivity,
                            "Exam added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        clearFields()
                        loadExams()

                    } else {

                        Toast.makeText(
                            this@ExamActivity,
                            "Failed to add exam.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    setLoading(false)

                    Toast.makeText(
                        this@ExamActivity,
                        "Unable to connect to StudyHub server.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun loadExams() {

        if (userId == -1L) return

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.getExams(userId)

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {

                        val exams = response.body() ?: emptyList()

                        displayExams(exams)

                    } else {

                        Toast.makeText(
                            this@ExamActivity,
                            "Unable to load exams.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@ExamActivity,
                        "Unable to connect to StudyHub server.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun displayExams(exams: List<Exam>) {

        examContainer.removeAllViews()

        if (exams.isEmpty()) {

            val emptyMessage = TextView(this)

            emptyMessage.text = "No exams added yet."
            emptyMessage.textSize = 16f
            emptyMessage.setTextColor(
                getColor(R.color.studyhub_secondary_text)
            )
            emptyMessage.setPadding(8, 16, 8, 16)

            examContainer.addView(emptyMessage)

            return
        }

        exams.forEach { exam ->

            val card = LinearLayout(this)

            card.orientation = LinearLayout.VERTICAL
            card.setPadding(20, 20, 20, 20)
            card.setBackgroundColor(
                getColor(R.color.studyhub_light_purple)
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(0, 0, 0, 16)

            card.layoutParams = params

            val title = TextView(this)

            title.text = exam.title
            title.textSize = 20f
            title.setTextColor(
                getColor(R.color.studyhub_purple)
            )
            title.setTypeface(null, android.graphics.Typeface.BOLD)

            card.addView(title)

            val date = TextView(this)

            date.text = "📅 ${exam.examDate}"
            date.textSize = 16f
            date.setPadding(0, 8, 0, 0)

            card.addView(date)

            if (!exam.description.isNullOrBlank()) {

                val description = TextView(this)

                description.text = exam.description
                description.textSize = 15f
                description.setPadding(0, 8, 0, 0)

                card.addView(description)
            }

            if (!exam.venue.isNullOrBlank()) {

                val venue = TextView(this)

                venue.text = "📍 ${exam.venue}"
                venue.textSize = 15f
                venue.setPadding(0, 8, 0, 0)

                card.addView(venue)
            }

            val deleteButton = Button(this)

            deleteButton.text = "DELETE"
            deleteButton.backgroundTintList =
                android.content.res.ColorStateList.valueOf(
                    getColor(R.color.studyhub_red)
                )

            deleteButton.setOnClickListener {
                deleteExam(exam.id)
            }

            card.addView(deleteButton)

            examContainer.addView(card)
        }
    }

    private fun deleteExam(examId: Long?) {

        if (examId == null) return

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.deleteExam(examId)

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@ExamActivity,
                            "Exam deleted.",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadExams()

                    } else {

                        Toast.makeText(
                            this@ExamActivity,
                            "Failed to delete exam.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@ExamActivity,
                        "Unable to connect to StudyHub server.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun clearFields() {

        etExamTitle.text.clear()
        etExamDescription.text.clear()
        etExamDate.text.clear()
        etExamVenue.text.clear()
    }

    private fun setLoading(isLoading: Boolean) {

        if (isLoading) {

            examsProgressBar.visibility = View.VISIBLE
            btnAddExam.isEnabled = false

        } else {

            examsProgressBar.visibility = View.GONE
            btnAddExam.isEnabled = true
        }
    }
}