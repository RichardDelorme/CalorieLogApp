package com.example.calorielog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val btn = findViewById<Button>(R.id.btnGetStarted)
        btn.setOnClickListener {
            startActivity(Intent(this, AddEntryActivity::class.java))
            finish()
        }

    }
}
