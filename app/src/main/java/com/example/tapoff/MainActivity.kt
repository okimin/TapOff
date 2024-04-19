package com.example.tapoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var counter: TextView
    private lateinit var timerTextView: TextView

    private var currentCount = 0
    private var timerRunning = false
    private var readyRunning = false
    private val timerDuration = 10000L
    private val readyDuration = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tapButton = findViewById<Button>(R.id.tapButton)
        val startButton = findViewById<Button>(R.id.startButton)
        val counter = findViewById<TextView>(R.id.counter)

        tapButton.setOnClickListener {
            //TODO : Check if timer is Running
            currentCount++
            counter.text = currentCount.toString()

        }

        startButton.setOnClickListener {
            startTimer()
            //TODO : Make the button invisible
        }

    }

    private fun startTimer() {
        readyRunning = true
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if(readyRunning){
                    val timeLeft = readyDuration - System.currentTimeMillis() % readyDuration
                    val seconds = (timeLeft / 1000).toInt()
                    if(seconds <= 0){
                        timerRunning = true
                        Toast.makeText(this@MainActivity, "Start!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this@MainActivity, "Ready in $seconds", Toast.LENGTH_SHORT).show()
                        handler.postDelayed(this, 1000) // Update timer text every second
                    }
                }
                else if (timerRunning) {
                    val timeLeft = timerDuration - System.currentTimeMillis() % timerDuration
                    val seconds = (timeLeft / 1000).toInt()
                    timerTextView.text = seconds.toString()
                    handler.postDelayed(this, 1000) // Update timer text every second
                } else {
                    // Timer finished, reset everything
                    timerTextView.text = "0"
                }
            }
        }
        handler.post(runnable)
    }



    private fun resetTimer() {
        timerRunning = false
        readyRunning = false
        timerTextView.text = "0"
    }

}