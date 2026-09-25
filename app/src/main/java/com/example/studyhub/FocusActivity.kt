package com.example.studyhub

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FocusActivity : AppCompatActivity() {

    private lateinit var txtTimer: TextView
    private lateinit var txtStatus: TextView
    private lateinit var btnStart: Button
    private lateinit var btnReset: android.widget.ImageButton

    private var timer: CountDownTimer? = null

    private var timeLeftMillis = 25 * 60 * 1000L

    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_focus)

        txtTimer = findViewById(R.id.txtTimer)
        txtStatus = findViewById(R.id.txtStatus)
        btnStart = findViewById(R.id.btnStart)
        btnReset = findViewById(R.id.btnReset)

        findViewById<Button>(R.id.btnFocus).setOnClickListener {
            resetTimer()
            txtStatus.text = "Focus session"
        }

        findViewById<Button>(R.id.btnBreak).setOnClickListener {
            resetTimer()
            timeLeftMillis = 5 * 60 * 1000L
            updateTimer()
            txtStatus.text = "Break session"
        }

        btnStart.setOnClickListener {
            if (running) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        btnReset.setOnClickListener {
            resetTimer()
        }

        findViewById<android.widget.ImageButton>(R.id.btnBack)
            .setOnClickListener {
                finish()
            }

        updateTimer()
    }

    private fun startTimer() {

        timer = object : CountDownTimer(
            timeLeftMillis,
            1000
        ) {

            override fun onTick(millisUntilFinished: Long) {

                timeLeftMillis = millisUntilFinished

                updateTimer()
            }

            override fun onFinish() {

                running = false

                timeLeftMillis = 0

                updateTimer()

                txtStatus.text = "Session completed! 🎉"

                btnStart.text = "▶"
            }

        }.start()

        running = true

        btnStart.text = "Ⅱ"

        txtStatus.text = "Stay focused..."
    }

    private fun pauseTimer() {

        timer?.cancel()

        running = false

        btnStart.text = "▶"

        txtStatus.text = "Paused"
    }

    private fun resetTimer() {

        timer?.cancel()

        running = false

        timeLeftMillis = 25 * 60 * 1000L

        btnStart.text = "▶"

        txtStatus.text = "Ready to focus"

        updateTimer()
    }

    private fun updateTimer() {

        val minutes = timeLeftMillis / 1000 / 60

        val seconds = timeLeftMillis / 1000 % 60

        txtTimer.text = String.format(
            "%02d:%02d",
            minutes,
            seconds
        )
    }

    override fun onDestroy() {

        timer?.cancel()

        super.onDestroy()
    }
}