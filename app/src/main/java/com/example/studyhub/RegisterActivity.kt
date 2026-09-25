package com.example.studyhub

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studyhub.model.User
import com.example.studyhub.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvBackToLogin: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etRegisterEmail)
        etPassword = findViewById(R.id.etRegisterPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
        progressBar = findViewById(R.id.registerProgressBar)

        btnRegister.setOnClickListener {
            registerUser()
        }

        tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {

        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

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

        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            etPassword.error = "Password must be at least 6 characters"
            etPassword.requestFocus()
            return
        }

        if (password != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            etConfirmPassword.requestFocus()
            return
        }

        setLoading(true)

        val user = User(
            name = name,
            email = email,
            password = password
        )

        RetrofitClient.apiService
            .registerUser(user)
            .enqueue(object : Callback<User> {

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {

                    setLoading(false)

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@RegisterActivity,
                            "Account created successfully!",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()

                    } else {

                        val errorBody =
                            response.errorBody()?.string()

                        val message = when (response.code()) {

                            400 ->
                                "Invalid registration details."

                            409 ->
                                "This email is already registered."

                            500 ->
                                "Server error. Check the Spring Boot console."

                            else ->
                                "Registration failed. Server code: ${response.code()}"
                        }

                        Toast.makeText(
                            this@RegisterActivity,
                            message,
                            Toast.LENGTH_LONG
                        ).show()

                        android.util.Log.e(
                            "STUDYHUB_REGISTER",
                            "HTTP ${response.code()} - $errorBody"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<User>,
                    t: Throwable
                ) {

                    setLoading(false)

                    android.util.Log.e(
                        "STUDYHUB_REGISTER",
                        "Connection error",
                        t
                    )

                    Toast.makeText(
                        this@RegisterActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun setLoading(isLoading: Boolean) {

        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            btnRegister.isEnabled = false
        } else {
            progressBar.visibility = View.GONE
            btnRegister.isEnabled = true
        }
    }
}