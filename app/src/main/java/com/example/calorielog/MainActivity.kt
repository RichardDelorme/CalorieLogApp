package com.example.calorielog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.calorielog.net.MealApi
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Load random meal image from TheMealDB
        val img = findViewById<ImageView>(R.id.imgMeal)
        lifecycleScope.launch {
            try {
                val meal = MealApi.create().randomMeal().meals?.firstOrNull()
                val url = meal?.strMealThumb
                if (url != null) Glide.with(this@MainActivity).load(url).into(img)
            } catch (_: Exception) {
                // Ignore for demo
            }
        }

        // Set up navigation buttons
        findViewById<Button>(R.id.btnAddEntry).setOnClickListener {
            startActivity(Intent(this, AddEntryActivity::class.java))
        }

        findViewById<Button>(R.id.btnDailyLog).setOnClickListener {
            startActivity(Intent(this, DailyLogActivity::class.java))
        }

        findViewById<Button>(R.id.btnWeeklyLog).setOnClickListener {
            startActivity(Intent(this, WeeklyLogActivity::class.java))
        }

        // Schedule the background reminder notification
        scheduleDailyReminder()
    }

    /** Schedules a daily WorkManager reminder at 8 PM. */
    private fun scheduleDailyReminder() {
        val now = LocalDateTime.now()
        val eightPm = now.withHour(20).withMinute(0).withSecond(0).withNano(0)
        val initialDelay = kotlin.math.max(
            0L,
            eightPm.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                    System.currentTimeMillis()
        )

        val request = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}



