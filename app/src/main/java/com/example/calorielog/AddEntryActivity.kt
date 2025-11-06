package com.example.calorielog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.calorielog.data.AppDatabase
import com.example.calorielog.model.FoodEntry
import java.time.LocalDate
import kotlinx.coroutines.launch

class AddEntryActivity : ComponentActivity() {

    // Persist the last chosen photo (saved with the entry)
    private var latestPhotoUri: String? = null

    // System file picker (no permission needed) for images
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        latestPhotoUri = uri?.toString()
        if (uri != null) {
            Toast.makeText(this, getString(R.string.image_attached), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        val editName = findViewById<EditText>(R.id.editFoodName)
        val editCals = findViewById<EditText>(R.id.editCalories)
        val btnSave  = findViewById<Button>(R.id.btnSave)
        val btnPhoto = findViewById<Button>(R.id.btnAttachPhoto)

        val dao = AppDatabase.get(this).foodDao()

        btnPhoto?.setOnClickListener {
            // Launch gallery/file picker for images
            pickImage.launch("image/*")
        }

        btnSave.setOnClickListener {
            val food = editName.text?.toString()?.trim().orEmpty()
            val cals = editCals.text?.toString()?.trim()

            val calories = cals?.toIntOrNull()
            if (food.isEmpty() || calories == null) {
                Toast.makeText(this, getString(R.string.enter_food_and_calories), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val btnViewLog = findViewById<Button>(R.id.btnViewDailyLog)
            btnViewLog.setOnClickListener {
                startActivity(Intent(this, DailyLogActivity::class.java))
            }


            val entry = FoodEntry(
                foodName = food,
                calories = calories,
                date = LocalDate.now().toString(),
                photoUri = latestPhotoUri
            )

            lifecycleScope.launch {
                dao.insert(entry)
                runOnUiThread {
                    Toast.makeText(this@AddEntryActivity, getString(R.string.saved), Toast.LENGTH_SHORT).show()
                    // reset inputs for next entry
                    editName.text?.clear()
                    editCals.text?.clear()
                    latestPhotoUri = null
                    editName.requestFocus()
                }
            }
        }
    }
}

