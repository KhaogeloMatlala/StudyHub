package com.example.studyhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studyhub.model.Subject
import com.example.studyhub.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubjectsActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etColor: EditText
    private lateinit var btnAdd: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var container: LinearLayout

    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_subjects)

        userId = intent.getLongExtra("USER_ID", -1L)

        if (userId == -1L) {
            Toast.makeText(
                this,
                "User session not found",
                Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }

        etName = findViewById(R.id.etSubjectName)
        etDescription = findViewById(R.id.etSubjectDescription)
        etColor = findViewById(R.id.etSubjectColor)
        btnAdd = findViewById(R.id.btnAddSubject)
        progressBar = findViewById(R.id.subjectsProgressBar)
        container = findViewById(R.id.subjectContainer)

        btnAdd.setOnClickListener {
            addSubject()
        }

        loadSubjects()
    }

    private fun addSubject() {

        val name = etName.text.toString().trim()

        if (name.isEmpty()) {
            etName.error = "Subject name is required"
            return
        }

        val subject = Subject(
            name = name,
            description = etDescription.text.toString().trim(),
            color = etColor.text.toString().trim()
        )

        progressBar.visibility = View.VISIBLE
        btnAdd.isEnabled = false

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.addSubject(
                        userId,
                        subject
                    )

                runOnUiThread {

                    progressBar.visibility = View.GONE
                    btnAdd.isEnabled = true

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@SubjectsActivity,
                            "Subject added!",
                            Toast.LENGTH_SHORT
                        ).show()

                        etName.text.clear()
                        etDescription.text.clear()
                        etColor.text.clear()

                        loadSubjects()

                    } else {

                        Toast.makeText(
                            this@SubjectsActivity,
                            "Failed to add subject",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {

                runOnUiThread {

                    progressBar.visibility = View.GONE
                    btnAdd.isEnabled = true

                    Toast.makeText(
                        this@SubjectsActivity,
                        "Connection error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun loadSubjects() {

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.getSubjects(userId)

                runOnUiThread {

                    if (response.isSuccessful) {
                        displaySubjects(
                            response.body() ?: emptyList()
                        )
                    }
                }

            } catch (e: Exception) {
                // Connection error handled when server is unavailable.
            }
        }
    }

    private fun displaySubjects(subjects: List<Subject>) {

        container.removeAllViews()

        if (subjects.isEmpty()) {

            val empty = TextView(this)

            empty.text = "No subjects yet."
            empty.textSize = 16f
            empty.setPadding(16, 16, 16, 16)

            container.addView(empty)

            return
        }

        subjects.forEach { subject ->

            val card = TextView(
                this
            )

            card.text =
                "${subject.name}\n${subject.description ?: ""}"

            card.textSize = 17f
            card.setTextColor(
                resources.getColor(
                    R.color.studyhub_text,
                    theme
                )
            )

            card.setPadding(20, 20, 20, 20)

            container.addView(card)

            val deleteButton = Button(this)

            deleteButton.text = "Delete"

            deleteButton.setOnClickListener {
                deleteSubject(subject.id)
            }

            container.addView(deleteButton)
        }
    }

    private fun deleteSubject(id: Long?) {

        if (id == null) return

        CoroutineScope(Dispatchers.IO).launch {

            val response =
                RetrofitClient.apiService.deleteSubject(id)

            runOnUiThread {

                if (response.isSuccessful) {
                    loadSubjects()
                }
            }
        }
    }
}