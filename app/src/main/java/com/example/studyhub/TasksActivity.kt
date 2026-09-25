package com.example.studyhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studyhub.model.Task
import com.example.studyhub.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksActivity : AppCompatActivity() {

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDescription: EditText
    private lateinit var etDueDate: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var btnAddTask: Button
    private lateinit var tasksProgressBar: ProgressBar
    private lateinit var taskContainer: LinearLayout

    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tasks)

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

        initialiseViews()
        setupPrioritySpinner()
        setupListeners()
        loadTasks()
    }

    private fun initialiseViews() {

        etTaskTitle = findViewById(R.id.etTaskTitle)
        etTaskDescription = findViewById(R.id.etTaskDescription)
        etDueDate = findViewById(R.id.etDueDate)
        spinnerPriority = findViewById(R.id.spinnerPriority)
        btnAddTask = findViewById(R.id.btnAddTask)
        tasksProgressBar = findViewById(R.id.tasksProgressBar)
        taskContainer = findViewById(R.id.taskContainer)
    }

    private fun setupPrioritySpinner() {

        val priorities = arrayOf(
            "High",
            "Medium",
            "Low"
        )

        spinnerPriority.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            priorities
        )
    }

    private fun setupListeners() {

        btnAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {

        val title = etTaskTitle.text.toString().trim()
        val description = etTaskDescription.text.toString().trim()
        val dueDate = etDueDate.text.toString().trim()
        val priority = spinnerPriority.selectedItem.toString()

        if (title.isEmpty()) {
            etTaskTitle.error = "Task title is required"
            etTaskTitle.requestFocus()
            return
        }

        if (dueDate.isEmpty()) {
            etDueDate.error = "Due date is required"
            etDueDate.requestFocus()
            return
        }

        val task = Task(
            title = title,
            description = description,
            priority = priority,
            completed = false,
            dueDate = dueDate
        )

        tasksProgressBar.visibility = View.VISIBLE
        btnAddTask.isEnabled = false

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.addTask(
                        userId,
                        task
                    )

                runOnUiThread {

                    tasksProgressBar.visibility = View.GONE
                    btnAddTask.isEnabled = true

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@TasksActivity,
                            "Task added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        clearFields()
                        loadTasks()

                    } else {

                        Toast.makeText(
                            this@TasksActivity,
                            "Failed to add task",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            } catch (e: Exception) {

                runOnUiThread {

                    tasksProgressBar.visibility = View.GONE
                    btnAddTask.isEnabled = true

                    Toast.makeText(
                        this@TasksActivity,
                        "Connection error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun loadTasks() {

        tasksProgressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService.getTasks(userId)

                runOnUiThread {

                    tasksProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {

                        displayTasks(
                            response.body() ?: emptyList()
                        )

                    } else {

                        Toast.makeText(
                            this@TasksActivity,
                            "Unable to load tasks",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {

                runOnUiThread {

                    tasksProgressBar.visibility = View.GONE

                    Toast.makeText(
                        this@TasksActivity,
                        "Unable to connect to StudyHub server",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun displayTasks(tasks: List<Task>) {

        taskContainer.removeAllViews()

        if (tasks.isEmpty()) {

            val emptyMessage = TextView(this)

            emptyMessage.text =
                "No tasks yet.\nAdd your first task above."

            emptyMessage.textSize = 16f
            emptyMessage.setPadding(16, 16, 16, 16)

            taskContainer.addView(emptyMessage)

            return
        }

        tasks.forEach { task ->

            val taskView = LayoutInflater
                .from(this)
                .inflate(
                    R.layout.item_task,
                    taskContainer,
                    false
                )

            val title =
                taskView.findViewById<TextView>(
                    R.id.tvItemTaskTitle
                )

            val description =
                taskView.findViewById<TextView>(
                    R.id.tvItemTaskDescription
                )

            val details =
                taskView.findViewById<TextView>(
                    R.id.tvItemTaskDetails
                )

            val completeButton =
                taskView.findViewById<Button>(
                    R.id.btnCompleteTask
                )

            val deleteButton =
                taskView.findViewById<Button>(
                    R.id.btnDeleteTask
                )

            title.text = task.title

            description.text =
                if (task.description.isNullOrBlank()) {
                    "No description"
                } else {
                    task.description
                }

            details.text =
                "Priority: ${task.priority}  •  Due: ${
                    task.dueDate ?: "No date"
                }"

            if (task.completed) {

                completeButton.text = "Completed"
                completeButton.isEnabled = false

            } else {

                completeButton.setOnClickListener {
                    completeTask(task.id)
                }
            }

            deleteButton.setOnClickListener {
                deleteTask(task.id)
            }

            taskContainer.addView(taskView)
        }
    }

    private fun completeTask(taskId: Long?) {

        if (taskId == null) return

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService
                        .completeTask(taskId)

                runOnUiThread {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@TasksActivity,
                            "Task completed!",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadTasks()

                    } else {

                        Toast.makeText(
                            this@TasksActivity,
                            "Could not complete task",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {

                runOnUiThread {
                    Toast.makeText(
                        this@TasksActivity,
                        "Connection error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun deleteTask(taskId: Long?) {

        if (taskId == null) return

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response =
                    RetrofitClient.apiService
                        .deleteTask(taskId)

                runOnUiThread {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@TasksActivity,
                            "Task deleted",
                            Toast.LENGTH_SHORT
                        ).show()

                        loadTasks()

                    } else {

                        Toast.makeText(
                            this@TasksActivity,
                            "Could not delete task",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {

                runOnUiThread {
                    Toast.makeText(
                        this@TasksActivity,
                        "Connection error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun clearFields() {

        etTaskTitle.text.clear()
        etTaskDescription.text.clear()
        etDueDate.text.clear()
        spinnerPriority.setSelection(0)
    }
}