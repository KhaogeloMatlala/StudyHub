package com.example.studyhub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studyhub.model.User
import com.example.studyhub.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var btnLogout: Button
    private lateinit var progressBar: ProgressBar

    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userId = intent.getLongExtra("USER_ID", -1L)

        etName = findViewById(R.id.etProfileName)
        etEmail = findViewById(R.id.etProfileEmail)
        etPassword = findViewById(R.id.etProfilePassword)
        etConfirmPassword = findViewById(R.id.etProfileConfirmPassword)
        btnSave = findViewById(R.id.btnSaveProfile)
        btnLogout = findViewById(R.id.btnLogout)
        progressBar = findViewById(R.id.profileProgressBar)

        btnSave.setOnClickListener {
            updateProfile()
        }

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun updateProfile() {

        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (userId == -1L) {
            Toast.makeText(
                this,
                "User session not found.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (name.isEmpty()) {
            etName.error = "Name is required"
            etName.requestFocus()
            return
        }

        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email address"
            etEmail.requestFocus()
            return
        }

        if (password.isNotEmpty()) {

            if (password.length < 6) {
                etPassword.error =
                    "Password must be at least 6 characters"
                etPassword.requestFocus()
                return
            }

            if (password != confirmPassword) {
                etConfirmPassword.error =
                    "Passwords do not match"
                etConfirmPassword.requestFocus()
                return
            }
        }

        setLoading(true)

        val updatedUser = User(
            name = name,
            email = email,
            password = password
        )

        RetrofitClient.apiService
            .updateUser(userId, updatedUser)
            .enqueue(object : Callback<User> {

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {

                    setLoading(false)

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@ProfileActivity,
                            "Settings updated successfully!",
                            Toast.LENGTH_LONG
                        ).show()

                        etPassword.text.clear()
                        etConfirmPassword.text.clear()

                    } else {

                        Toast.makeText(
                            this@ProfileActivity,
                            "Unable to update settings.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<User>,
                    t: Throwable
                ) {

                    setLoading(false)

                    Toast.makeText(
                        this@ProfileActivity,
                        "Unable to connect to StudyHub server.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun logout() {

        val intent = Intent(
            this,
            MainActivity::class.java
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        finish()
    }

    private fun setLoading(isLoading: Boolean) {

        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            btnSave.isEnabled = false
        } else {
            progressBar.visibility = View.GONE
            btnSave.isEnabled = true
        }
    }
}