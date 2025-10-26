package com.example.calorielog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.calorielog.data.AppDatabase
import com.example.calorielog.model.FoodEntry
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddEntryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        val name = findViewById<EditText>(R.id.editFoodName)
        val cal  = findViewById<EditText>(R.id.editCalories)
        val btn  = findViewById<Button>(R.id.btnSave)
        val btnViewLog = findViewById<Button>(R.id.btnViewLog)
        btnViewLog.setOnClickListener {
            startActivity(Intent(this, DailyLogActivity::class.java))
        }

        val dao = AppDatabase.get(this).foodDao()

        btn.setOnClickListener {
            val food = name.text.toString().trim()
            val cals = cal.text.toString().toIntOrNull()
            if (food.isEmpty() || cals == null) {
                Toast.makeText(this, "Enter food and calories", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                dao.insert(
                    FoodEntry(
                        foodName = food,
                        calories = cals,
                        date = LocalDate.now().toString() // yyyy-MM-dd
                    )
                )
                runOnUiThread {
                    Toast.makeText(this@AddEntryActivity, "Saved", Toast.LENGTH_SHORT).show()
                    name.text.clear(); cal.text.clear()
                    name.requestFocus()
                }
            }
        }
    }
}
