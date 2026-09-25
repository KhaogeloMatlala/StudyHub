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

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            loginUser()
        }

        tvRegister.setOnClickListener {
            val intent = android.content.Intent(
                this,
                RegisterActivity::class.java
            )

            startActivity(intent)
        }
    }

    private fun loginUser() {

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email address"
            etEmail.requestFocus()
            return
        }

        setLoading(true)

        val loginUser = User(
            name = "",
            email = email,
            password = password
        )

        RetrofitClient.apiService.loginUser(loginUser)
            .enqueue(object : Callback<User> {

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    setLoading(false)

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@MainActivity,
                            "Login successful!",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = android.content.Intent(
                            this@MainActivity,
                            DashboardActivity::class.java
                        )

                        intent.putExtra(
                            "USER_ID",
                            response.body()?.id ?: -1L
                        )

                        startActivity(intent)
                        finish()
                    } else {

                        Toast.makeText(
                            this@MainActivity,
                            "Login failed. Check your email and password.",
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
                        this@MainActivity,
                        "Unable to connect to StudyHub server.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun setLoading(isLoading: Boolean) {

        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            btnLogin.isEnabled = false
        } else {
            progressBar.visibility = View.GONE
            btnLogin.isEnabled = true
        }
    }
}