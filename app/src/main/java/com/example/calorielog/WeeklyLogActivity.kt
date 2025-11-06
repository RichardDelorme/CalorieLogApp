package com.example.calorielog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorielog.data.AppDatabase
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeeklyLogActivity : ComponentActivity() {
    private val fmt = DateTimeFormatter.ISO_LOCAL_DATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_log)

        val recycler = findViewById<RecyclerView>(R.id.recyclerWeek)
        val txtRange = findViewById<TextView>(R.id.txtWeekRange)
        val btnHome  = findViewById<Button>(R.id.btnHomeWeek)
        val btnReset = findViewById<Button>(R.id.btnResetWeek)

        val adapter  = DayTotalAdapter(emptyList())
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val today = LocalDate.now()
        val start = today.with(DayOfWeek.MONDAY)
        val end   = today.with(DayOfWeek.SUNDAY)
        txtRange.text = getString(
            R.string.week_range,
            start.format(fmt),
            end.format(fmt)
        )


        val dao = AppDatabase.get(this).foodDao()
        dao.entriesBetween(start.format(fmt), end.format(fmt)).observe(this) { entries ->
            val grouped = entries.groupBy { it.date }
                .map { (d, list) -> DayTotal(d, list.sumOf { it.calories }) }
                .sortedByDescending { it.date }
            adapter.update(grouped)
        }

        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnReset.setOnClickListener {
            lifecycleScope.launch {
                dao.deleteBetween(start.format(fmt), end.format(fmt))
                runOnUiThread {
                    Toast.makeText(this@WeeklyLogActivity, "Week cleared", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

