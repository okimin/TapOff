package com.example.tapoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val currentNumber = findViewById<TextView>(R.id.counter)
        var counter = 0
        button.setOnClickListener {
            counter++
            currentNumber.text = counter.toString()
        }

    }
}