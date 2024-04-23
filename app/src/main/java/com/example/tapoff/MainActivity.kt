package com.example.tapoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var counter: TextView

    private lateinit var timerTextView: TextView
    private lateinit var startButton : Button
    private lateinit var recordTextView : TextView


    private var currentCount = 0
    private var currentRecord = 0
    private var readyRunning = false
    private var isReady = false
    private var startTime: Long = 0L
    private val timerDuration = 10000L
    private val readyDuration = 3000L
    private val handler = Handler(Looper.getMainLooper())
    private val preChallengeRunnable = object : Runnable {
        override fun run() {
            if (readyRunning) {
                val timeLeft = readyDuration - (System.currentTimeMillis() - (startTime))
                val seconds = (timeLeft / 1000).toInt()
                if (timeLeft <= 0) {
                    timerTextView.isVisible = true
                    isReady = true
                    Toast.makeText(this@MainActivity, "Start!", Toast.LENGTH_SHORT).show()
                    handler.removeCallbacks(this) // Stop pre-challenge timer
                    startTime = System.currentTimeMillis()
                    handler.post(challengeRunnable) // Start challenge timer after 1 second
                } else {
                    Toast.makeText(this@MainActivity, "Ready in $seconds", Toast.LENGTH_SHORT).show()
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }

    private val challengeRunnable = object : Runnable {
        override fun run() {
            if (isReady) {
                val timeLeft = timerDuration - (System.currentTimeMillis() - (startTime))
                val seconds = (timeLeft / 1000).toInt()
                timerTextView.text = "Time Remaining: $seconds"
                if (timeLeft <= 0) {
                    if(currentCount > currentRecord)
                        currentRecord = currentCount
                    resetTimer()
                } else {
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tapButton = findViewById<Button>(R.id.tapButton)
        timerTextView = findViewById<TextView>(R.id.timeLeftText)
        recordTextView = findViewById<TextView>(R.id.recordNumber)
        timerTextView.isVisible =  false
        startButton = findViewById<Button>(R.id.startButton)
        counter = findViewById<TextView>(R.id.counter)

        tapButton.setOnClickListener {
            //TODO : Check if timer is Running
            if(isReady) {
                currentCount++
                counter.text = currentCount.toString()
            }

        }

        startButton.setOnClickListener {
            startButton.isEnabled = false
            startTime = System.currentTimeMillis()
            startTimer()
        }

    }

    private fun startTimer() {
        readyRunning = true
        handler.post(preChallengeRunnable)
    }



    private fun resetTimer() {
        readyRunning = false
        isReady = false
        timerTextView.isVisible = false
        timerTextView.text = "Time Remaining: 10"
        recordTextView.text = "Current Record: $currentRecord"
        startButton.isEnabled = true
        currentCount = 0
        counter.text = currentCount.toString()
        handler.removeCallbacks(preChallengeRunnable)
        handler.removeCallbacks(challengeRunnable)
    }

}