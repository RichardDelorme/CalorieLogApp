package com.example.calorielog

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorielog.data.AppDatabase
import java.time.LocalDate

class DailyLogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_log)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        val txtDate  = findViewById<TextView>(R.id.txtDate)
        val adapter  = EntryAdapter(emptyList())

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val today = LocalDate.now().toString()
        txtDate.text = getString(R.string.entries_for, today)

        val dao = AppDatabase.get(this).foodDao()
        dao.entriesForDate(today).observe(this) { entries ->
            adapter.update(entries)
        }
    }
}
