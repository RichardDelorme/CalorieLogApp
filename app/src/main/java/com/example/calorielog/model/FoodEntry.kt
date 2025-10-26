package com.example.calorielog.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class FoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val foodName: String,
    val calories: Int,
    val date: String // yyyy-MM-dd
)
