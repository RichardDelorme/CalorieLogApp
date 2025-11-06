package com.example.calorielog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorielog.data.AppDatabase
import com.example.calorielog.model.FoodEntry
import kotlinx.coroutines.launch
import java.time.LocalDate

class DailyLogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_log)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        val txtDate  = findViewById<TextView>(R.id.txtDate)
        val txtTotal = findViewById<TextView>(R.id.txtDailyTotal)
        val btnHome  = findViewById<Button>(R.id.btnHome)
        val btnReset = findViewById<Button>(R.id.btnResetDay)

        val adapter = EntryAdapter(emptyList())
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val today = LocalDate.now().toString()
        txtDate.text = getString(R.string.entries_for, today)

        // ViewModel setup
        val vm = ViewModelProvider(
            this,
            DailyLogVMFactory(AppDatabase.get(this))
        ).get(DailyLogViewModel::class.java)

        // Observe LiveData (explicit types for clarity)
        vm.entries.observe(this) { entries: List<FoodEntry> ->
            adapter.update(entries)
        }

        vm.total.observe(this) { total: Int ->
            txtTotal.text = getString(R.string.total_calories_value, total)
        }

        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnReset.setOnClickListener {
            lifecycleScope.launch {
                vm.resetDay()
                runOnUiThread {
                    Toast.makeText(this@DailyLogActivity, getString(R.string.day_cleared), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}



